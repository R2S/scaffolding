package br.ufscar.sagui.scaffolding.extractor.constraints

import br.ufscar.sagui.scaffolding.meta.field.FieldScaffold
import br.ufscar.sagui.scaffolding.meta.field.TypeFieldScaffold
import br.ufscar.sagui.scaffolding.meta.field.constraint.*
import br.ufscar.sagui.scaffolding.meta.field.params.EmailParamsFieldScaffold
import org.codehaus.groovy.grails.validation.EmailConstraint

/**
 * Created by raphael on 04/08/15.
 */
class ConstraintsGrailsWapper {

    Constraint getBindableConstr(def constraintsField) {
        def value = getValue(constraintsField, "bindable")
        Constraint constraint
        if (value) {
            constraint = new BindableConstraint()
            constraint.value = true
        }
        return constraint
    }

    Constraint getBlankConstr(def constraintsField) {
        def value = getValue(constraintsField, "blank")
        Constraint constraint
        if (value) {
            constraint = new BlankConstraint()
            org.codehaus.groovy.grails.validation.BlankConstraint blankConstraint = value
            constraint.value = blankConstraint.isBlank()
        }
        return constraint
    }

    Constraint getInListConstr(def constraintsField) {
        def value = getValue(constraintsField, "inList")
        Constraint constraint
        if (value) {
            constraint = new InListConstraint()
            org.codehaus.groovy.grails.validation.InListConstraint inListConstraint = value
            constraint.value = inListConstraint.getList()
        }
        return constraint
    }

    Constraint getMatchesConstr(def constraintsField) {
        def value = getValue(constraintsField, "matches")
        Constraint constraint
        if (value) {
            constraint = new MatchesConstraint()
            org.codehaus.groovy.grails.validation.MatchesConstraint matchesConstraint = value
            constraint.value = matchesConstraint.getRegex()
        }
        return constraint
    }

    Constraint getMaxConstr(def constraintsField) {
        def value = getValue(constraintsField, "max")
        Constraint constraint
        if (value) {
            constraint = new MaxConstraint()
            org.codehaus.groovy.grails.validation.MaxConstraint maxConstraint = value
            constraint.value = maxConstraint.getMaxValue()
        }
        return constraint
    }

    Constraint getMaxSizeConstr(def constraintsField) {
        def value = getValue(constraintsField, "maxSize")
        Constraint constraint
        if (value) {
            constraint = new MaxSizeConstraint()
            org.codehaus.groovy.grails.validation.MaxSizeConstraint maxSizeConstraint = value
            constraint.value = maxSizeConstraint.getMaxSize()
        }
        return constraint
    }

    Constraint getMinConstr(def constraintsField) {
        def value = getValue(constraintsField, "min")
        Constraint constraint
        if (value) {
            constraint = new MinConstraint()
            org.codehaus.groovy.grails.validation.MinConstraint minConstraint = value
            constraint.value = minConstraint.getMinValue()
        }
        return constraint
    }

    Constraint getMinSizeConstr(def constraintsField) {
        def value = getValue(constraintsField, "minSize")
        Constraint constraint
        if (value) {
            constraint = new MinSizeConstraint()
            org.codehaus.groovy.grails.validation.MinSizeConstraint minSizeConstraint = value
            constraint.value = minSizeConstraint.getMinSize()
        }
        return constraint
    }

    Constraint getNotEqualConstr(def constraintsField) {
        def value = getValue(constraintsField, "notEqual")
        Constraint constraint
        if (value) {
            constraint = new NotEqualConstraint()
            org.codehaus.groovy.grails.validation.NotEqualConstraint notEqualConstraint = value
            constraint.value = notEqualConstraint.getNotEqualTo()
        }
        return constraint
    }

    Constraint getNullableConstr(def constraintsField) {
        def value = getValue(constraintsField, "nullable")
        Constraint constraint
        if (value) {
            constraint = new NullableConstraint()
            org.codehaus.groovy.grails.validation.NullableConstraint nullableConstraint = value
            constraint.value = nullableConstraint.isNullable()
        }
        return constraint
    }

    Constraint getRangeConstr(def constraintsField) {
        def value = getValue(constraintsField, "range")
        Constraint constraint
        if (value) {
            constraint = new RangeConstraint()
            org.codehaus.groovy.grails.validation.RangeConstraint rangeConstraint = value
            constraint.value = rangeConstraint.getRange()
        }
        return constraint
    }

    Constraint getScalaConstr(def constraintsField) {
        def value = getValue(constraintsField, "scale")
        Constraint constraint
        if (value) {
            constraint = new ScaleConstraint()
            org.codehaus.groovy.grails.validation.ScaleConstraint scaleConstraint = value
            constraint.value = scaleConstraint.getScale()
        }
        return constraint
    }

    Constraint getSizeConstr(def constraintsField) {
        def value = getValue(constraintsField, "size")
        Constraint constraint
        if (value) {
            constraint = new SizeConstraint()
            org.codehaus.groovy.grails.validation.SizeConstraint sizeConstraint = value
            constraint.value = sizeConstraint.getRange()
        }
        return constraint
    }

    Constraint getUniqueConstr(def constraintsField) {
        def value = getValue(constraintsField, "unique")
        Constraint constraint
        if (value) {
            constraint = new UniqueConstraint()
            constraint.value = value
        }
        return constraint
    }


    private def getValue(def constraintsField, String constraint) {
        constraintsField?.getAppliedConstraint(constraint)
    }

    void changeEmailParams(def constraintsField, FieldScaffold fieldScaffold) {
        def value = getValue(constraintsField, "email")
        if (value) {
            EmailConstraint emailConstraint = value
            if (emailConstraint.email) {
                fieldScaffold.type = TypeFieldScaffold.EMAIL
                def paramsEmail = new EmailParamsFieldScaffold(fieldScaffold.params.properties)
                fieldScaffold.params = paramsEmail
            }
        }
    }
}
