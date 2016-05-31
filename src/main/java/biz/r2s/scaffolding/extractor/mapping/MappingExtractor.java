package br.ufscar.sagui.scaffolding.extractor.mapping

import br.ufscar.sagui.scaffolding.extractor.MetaDomainExtractor
import br.ufscar.sagui.scaffolding.meta.ClassScaffold
import br.ufscar.sagui.scaffolding.meta.StatusClassScaffold
import br.ufscar.sagui.scaffolding.meta.datatatable.OrderDatatable
import br.ufscar.sagui.scaffolding.meta.field.FieldScaffold
import br.ufscar.sagui.scaffolding.meta.field.TypeFieldScaffold
import br.ufscar.sagui.scaffolding.meta.field.constraint.NullableConstraint
import br.ufscar.sagui.scaffolding.meta.field.constraint.ScaleConstraint
import br.ufscar.sagui.scaffolding.meta.field.constraint.SizeConstraint
import br.ufscar.sagui.scaffolding.meta.field.constraint.UniqueConstraint
import br.ufscar.sagui.scaffolding.meta.field.params.DataTableParamsFieldScaffold
import br.ufscar.sagui.util.ClosureUtil
import br.ufscar.sagui.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty

/**
 * Created by raphael on 28/07/15.
 */
class MappingExtractor implements MetaDomainExtractor {

    def includeCamposMapping = ["autoImport", "autoTimestamp",
                                "batchSize", "cache", "comment", "discriminator", "dynamicInsert",
                                "dynamicUpdate", "id", "order", "sort", "table", "version", "datasource"]

    @Override
    ClassScaffold extractor(GrailsDomainClass domainClass, ClassScaffold classScaffold) {
        def mapping = getPropertyMapping(domainClass.clazz)
        if (mapping) {
            def configMapping = ClosureUtil.toMapConfig(mapping, domainClass.clazz, includeCamposMapping)

            if (configMapping) {
                applyConfigClassScaffolding(configMapping, classScaffold)
                for (GrailsDomainClassProperty property : GrailsUtil.getPersistentProperties(domainClass)) {
                    FieldScaffold fieldScaffold = this.getCampus(classScaffold, property.name)
                    def mappingField = configMapping."$property.name"
                    if(fieldScaffold){
                        applyConfigFieldScaffolding(mappingField, fieldScaffold)
                    }
                }
            }
        }

        return classScaffold
    }

    def applyConfigClassScaffolding(def configMapping, ClassScaffold classScaffold){
        this.addAutoImportMapping(configMapping, classScaffold)
        this.addBatchSizeClassMapping(configMapping, classScaffold)
        this.addOrderMapping(configMapping, classScaffold)
        this.addSortMapping(configMapping, classScaffold)
    }
    def applyConfigFieldScaffolding(def mappingField, FieldScaffold fieldScaffold){
        if(fieldScaffold.isTypeHasMany()){
            DataTableParamsFieldScaffold dataTableParamsFieldScaffold = fieldScaffold.params
            Class clazzHasMany = GrailsUtil.getDomainClass(fieldScaffold.parent.clazz)?.getPropertyByName(fieldScaffold.key)?.referencedDomainClass?.clazz
            def mappingHasMany = getPropertyMapping(clazzHasMany)
            if(mappingHasMany){
                def configMappingHasMany = ClosureUtil.toMapConfig(mappingHasMany, clazzHasMany, includeCamposMapping)
                ClassScaffold classScaffoldHasMany = new ClassScaffold(datatable: dataTableParamsFieldScaffold)
                applyConfigClassScaffolding(configMappingHasMany, classScaffoldHasMany)
            }
        }

        if (mappingField) {
                this.addBatchSizeMapping(mappingField, fieldScaffold)
                this.addIgnoreNotFoundMapping(mappingField, fieldScaffold)
                this.addInsertableMapping(mappingField, fieldScaffold)
                this.addUpdateableMapping(mappingField, fieldScaffold)
                this.addUniqueMapping(mappingField, fieldScaffold)
                this.addLengthMapping(mappingField, fieldScaffold)
                this.addScaleMapping(mappingField, fieldScaffold)
                this.addDefaultValueMapping(mappingField, fieldScaffold)
                this.addSortFieldMapping(mappingField, fieldScaffold)
                this.addOrderFieldMapping(mappingField, fieldScaffold)
        }
    }



    def getPropertyMapping(Class clazz){
        def mapping = null
        try{
            mapping = clazz.mapping
        }catch (MissingPropertyException e){
            mapping = null
        }
        return mapping
    }


    FieldScaffold getCampus(ClassScaffold classScaffold, String campus) {
        FieldScaffold fieldScaffold = null
        for (FieldScaffold field : classScaffold.fields) {
            if (field.key == campus) {
                fieldScaffold = field
                break
            }
        }
        return fieldScaffold
    }

    void addAutoImportMapping(def configMapping, ClassScaffold classScaffold) {
        def value = configMapping.get("autoImport")
        if (value != null) {
            if (value == false) {
                classScaffold.setStatus(StatusClassScaffold.NOT_MAPPING)
            }
        }
    }

    void addBatchSizeClassMapping(def configMapping, ClassScaffold classScaffold) {
        def value = configMapping.get("batchSize")
        if (value != null) {
            if (value == false) {
                classScaffold.datatable.numMaxPaginate = value
            }
        }
    }

    void addOrderMapping(def configMapping, ClassScaffold classScaffold) {
        def value = configMapping.get("order")
        if (value != null) {
            classScaffold.datatable.order = value == 'desc' ? OrderDatatable.DESC : OrderDatatable.ASC
        }
    }

    void addSortMapping(def configMapping, ClassScaffold classScaffold) {
        def value = configMapping.get("sort")
        if (value != null) {
            classScaffold.datatable.sort = value
        }
    }

    void addOrderFieldMapping(def mappingField, FieldScaffold fieldScaffold) {
        def value = mappingField.get("order")
        if (value != null&&fieldScaffold.type==TypeFieldScaffold.DATATABLE) {
            DataTableParamsFieldScaffold params = fieldScaffold.params
            params.order = value == 'desc' ? OrderDatatable.DESC : OrderDatatable.ASC
        }
    }

    void addSortFieldMapping(def mappingField, FieldScaffold fieldScaffold) {
        def value = mappingField.get("sort")
        if (value != null&&fieldScaffold.type==TypeFieldScaffold.DATATABLE) {
            DataTableParamsFieldScaffold params = fieldScaffold.params
            params.sort = value
        }
    }

    void addBatchSizeMapping(def mappingField, FieldScaffold fieldScaffold) {
        def value = mappingField.get("batchSize")
        if (value != null) {
            if (value == false) {
                DataTableParamsFieldScaffold params = fieldScaffold.params
                params.numMaxPaginate = value
            }
        }
    }

    void addIgnoreNotFoundMapping(def mappingField, FieldScaffold fieldScaffold) {
        def value = mappingField.get("ignoreNotFound")
        if (value != null) {
            NullableConstraint constraint = new NullableConstraint()
            constraint.value = value
            constraint.parent = fieldScaffold
            fieldScaffold.constraints << constraint
        }
    }

    void addInsertableMapping(def mappingField, FieldScaffold fieldScaffold) {
        def value = mappingField.get("insertable")
        if (value != null) {
            fieldScaffold.insertable = value
        }
    }

    void addUpdateableMapping(def mappingField, FieldScaffold fieldScaffold) {
        def value = mappingField.get("updateable")
        if (value != null) {
            fieldScaffold.updateable = value
        }
    }

    void addUniqueMapping(def mappingField, FieldScaffold fieldScaffold) {
        def value = mappingField.get("unique")
        if (value != null) {
            UniqueConstraint constraint = new UniqueConstraint()
            constraint.value = value
            constraint.parent = fieldScaffold
            fieldScaffold.constraints << constraint
        }
    }

    void addLengthMapping(def mappingField, FieldScaffold fieldScaffold) {
        def value = mappingField.get("length")
        if (value != null) {
            SizeConstraint constraint = new SizeConstraint()
            constraint.value = value
            constraint.parent = fieldScaffold
            fieldScaffold.constraints << constraint
        }
    }

    void addScaleMapping(def mappingField, FieldScaffold fieldScaffold) {
        def value = mappingField.get("scale")
        if (value != null) {
            ScaleConstraint constraint = new ScaleConstraint()
            constraint.value = value
            constraint.parent = fieldScaffold
            fieldScaffold.constraints << constraint
        }
    }

    void addDefaultValueMapping(def mappingField, FieldScaffold fieldScaffold) {
        def value = mappingField.get("defaultValue")
        if (value != null) {
            fieldScaffold.defaultValue = value
        }
    }
}
