package br.ufscar.sagui.scaffolding.extractor.clazz

import br.ufscar.sagui.scaffolding.meta.ResourceUrlScaffold
import br.ufscar.sagui.scaffolding.meta.TitleScaffold
import br.ufscar.sagui.scaffolding.meta.datatatable.CampoDatatable
import br.ufscar.sagui.scaffolding.meta.datatatable.DatatableScaffold
import br.ufscar.sagui.scaffolding.meta.datatatable.OrderDatatable
import br.ufscar.sagui.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty

/**
 * Created by raphael on 30/07/15.
 */
class DatatableClassExtrator {

    static final FIELDS_EXCLUDE = ['version']

    private TitleClassExtractor titleClassBuilder

    public DatatableClassExtrator() {
        titleClassBuilder = new TitleClassExtractor()
    }

    public void initDatatableDefault(DatatableScaffold dataTable, GrailsDomainClass domainClass, String propertyHasMany = null) {
        dataTable.setPagination(true)
        dataTable.setSearchable(isSearchable(domainClass))
        dataTable.setSortable(true)
        dataTable.setOrdenate(true)
        dataTable.setSort("id")
        dataTable.setOrder(OrderDatatable.DESC)
        dataTable.setTitle(this.getTitle(domainClass))
        dataTable.setNumMaxPaginate(10)
        dataTable.setResourceUrlScaffold(ResourceUrlScaffold.builder(domainClass, propertyHasMany))
        dataTable.setColumns(this.getColumns(domainClass, dataTable))

    }

    private TitleScaffold getTitle(GrailsDomainClass domainClass) {
        return titleClassBuilder.getTitle(domainClass)
    }


    private boolean isSearchable(GrailsDomainClass domainClass) {
        try {
            return (domainClass?.clazz?."searchable" != null)
        } catch (Exception e) {
            return false
        }
    }

    private List<CampoDatatable> getColumns(GrailsDomainClass domainClass, DatatableScaffold dataTable) {
        List<CampoDatatable> campos = []
        int count = 0
        GrailsUtil.getPersistentProperties(domainClass).each {prop->
            if(validateColumn(prop)){
                count++
                CampoDatatable campo = new CampoDatatable()
                campo.key = prop.fieldName
                campo.name = prop.name
                campo.title = prop.naturalName
                campo.parent = dataTable
                campo.order = count
                campos << campo
            }
        }
        return campos
    }

    private boolean validateColumn(GrailsDomainClassProperty property){
        !(property.isOneToMany()||property.name in FIELDS_EXCLUDE)
    }
}
