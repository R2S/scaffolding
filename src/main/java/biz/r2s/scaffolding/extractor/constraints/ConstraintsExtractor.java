package br.ufscar.sagui.scaffolding.extractor.constraints

import br.ufscar.sagui.scaffolding.extractor.MetaDomainExtractor
import br.ufscar.sagui.scaffolding.meta.ClassScaffold
import br.ufscar.sagui.scaffolding.meta.field.FieldScaffold
import br.ufscar.sagui.scaffolding.meta.field.constraint.Constraint
import br.ufscar.sagui.util.GrailsUtil
import org.codehaus.groovy.grails.commons.GrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty

/**
 * Created by raphael on 28/07/15.
 */
class ConstraintsExtractor implements MetaDomainExtractor {

    ConstraintsGrailsWapper constWapper

    public ConstraintsExtractor() {
        constWapper = new ConstraintsGrailsWapper()
    }

    @Override
    ClassScaffold extractor(GrailsDomainClass domainClass, ClassScaffold classScaffold) {
        def constraints = domainClass.clazz.constraints
        if (constraints) {
            for (GrailsDomainClassProperty property : GrailsUtil.getPersistentProperties(domainClass)) {
                def constraintsField = constraints."$property.name"
                if (constraintsField) {
                    FieldScaffold fieldScaffold = this.getCampus(classScaffold, property.name)
                    if (fieldScaffold) {
                        this.addContrainst(constWapper.getBindableConstr(constraintsField), fieldScaffold)
                        this.addContrainst(constWapper.getBlankConstr(constraintsField), fieldScaffold)
                        this.addContrainst(constWapper.getInListConstr(constraintsField), fieldScaffold)
                        this.addContrainst(constWapper.getMatchesConstr(constraintsField), fieldScaffold)
                        this.addContrainst(constWapper.getMaxConstr(constraintsField), fieldScaffold)
                        this.addContrainst(constWapper.getMinConstr(constraintsField), fieldScaffold)
                        this.addContrainst(constWapper.getMaxSizeConstr(constraintsField), fieldScaffold)
                        this.addContrainst(constWapper.getMinSizeConstr(constraintsField), fieldScaffold)
                        this.addContrainst(constWapper.getNotEqualConstr(constraintsField), fieldScaffold)
                        this.addContrainst(constWapper.getNullableConstr(constraintsField), fieldScaffold)
                        this.addContrainst(constWapper.getRangeConstr(constraintsField), fieldScaffold)
                        this.addContrainst(constWapper.getScalaConstr(constraintsField), fieldScaffold)
                        this.addContrainst(constWapper.getSizeConstr(constraintsField), fieldScaffold)
                        this.addContrainst(constWapper.getUniqueConstr(constraintsField), fieldScaffold)
                        constWapper.changeEmailParams(constraintsField, fieldScaffold)
                    }
                }
            }
        }
        return classScaffold
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

    void addContrainst(Constraint valor, FieldScaffold fieldScaffold) {
        if (valor) {
            valor.parent = fieldScaffold
            fieldScaffold.constraints << valor

        }
    }


}
