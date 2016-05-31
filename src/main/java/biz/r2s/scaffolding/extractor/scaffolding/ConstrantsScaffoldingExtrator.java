package br.ufscar.sagui.scaffolding.extractor.scaffolding

import br.ufscar.sagui.scaffolding.meta.field.FieldScaffold
import br.ufscar.sagui.scaffolding.meta.field.constraint.Constraint
import br.ufscar.sagui.scaffolding.meta.field.constraint.ConstraintFactory
import br.ufscar.sagui.scaffolding.meta.field.constraint.TypeContraint

/**
 * Created by raphael on 06/08/15.
 */
class ConstrantsScaffoldingExtrator {
    void chengeConstraintsField(def fieldScaffolding, FieldScaffold fieldScaffold) {
        this.addConstraint(this.getBindableExtractor(fieldScaffolding), fieldScaffold)
        this.addConstraint(this.getBlankExtractor(fieldScaffolding), fieldScaffold)
        this.addConstraint(this.getGroupRequestExtractor(fieldScaffolding), fieldScaffold)
        this.addConstraint(this.getInListExtractor(fieldScaffolding), fieldScaffold)
        this.addConstraint(this.getMatchesExtractor(fieldScaffolding), fieldScaffold)
        this.addConstraint(this.getMaxExtractor(fieldScaffolding), fieldScaffold)
        this.addConstraint(this.getMaxSizeExtractor(fieldScaffolding), fieldScaffold)
        this.addConstraint(this.getMinExtractor(fieldScaffolding), fieldScaffold)
        this.addConstraint(this.getMinSizeExtractor(fieldScaffolding), fieldScaffold)
        this.addConstraint(this.getNotEqualExtractor(fieldScaffolding), fieldScaffold)
        this.addConstraint(this.getNullableExtractor(fieldScaffolding), fieldScaffold)
        this.addConstraint(this.getRangeExtractor(fieldScaffolding), fieldScaffold)
        this.addConstraint(this.getScaleExtractor(fieldScaffolding), fieldScaffold)
        this.addConstraint(this.getSizeExtractor(fieldScaffolding), fieldScaffold)
        this.addConstraint(this.getUniqueExtractor(fieldScaffolding), fieldScaffold)
    }

    void addConstraint(Constraint constraint, FieldScaffold fieldScaffold) {
        if (constraint) {
            Constraint constraint1 = fieldScaffold.constraints.find({it.type == constraint.type})
            if(constraint1){
                constraint1.value = constraint.value
            }else{
                fieldScaffold.constraints << constraint
            }
        }
    }

    Constraint getConstraintDefalt(def fieldScaffolding, TypeContraint typeContraint) {
        def value = fieldScaffolding.get(typeContraint.name)
        Constraint constraint = null
        if (value!=null) {
            constraint = ConstraintFactory.factory(typeContraint)
            if (constraint) {
                constraint.value = value
            }
        }
        return constraint
    }

    Constraint getBindableExtractor(def fieldScaffolding) {
        this.getConstraintDefalt(fieldScaffolding, TypeContraint.BINDABLE)
    }

    Constraint getBlankExtractor(def fieldScaffolding) {
        this.getConstraintDefalt(fieldScaffolding, TypeContraint.BLANK)
    }

    Constraint getGroupRequestExtractor(def fieldScaffolding) {
        this.getConstraintDefalt(fieldScaffolding, TypeContraint.GROUP_REQUERED)
    }

    Constraint getInListExtractor(def fieldScaffolding) {
        this.getConstraintDefalt(fieldScaffolding, TypeContraint.INLIST)
    }

    Constraint getMatchesExtractor(def fieldScaffolding) {
        this.getConstraintDefalt(fieldScaffolding, TypeContraint.MATCHES)
    }

    Constraint getMaxExtractor(def fieldScaffolding) {
        this.getConstraintDefalt(fieldScaffolding, TypeContraint.MAX)
    }

    Constraint getMaxSizeExtractor(def fieldScaffolding) {
        this.getConstraintDefalt(fieldScaffolding, TypeContraint.MAXSIZE)
    }

    Constraint getMinExtractor(def fieldScaffolding) {
        this.getConstraintDefalt(fieldScaffolding, TypeContraint.MIN)
    }

    Constraint getMinSizeExtractor(def fieldScaffolding) {
        this.getConstraintDefalt(fieldScaffolding, TypeContraint.MINSIZE)
    }

    Constraint getNotEqualExtractor(def fieldScaffolding) {
        this.getConstraintDefalt(fieldScaffolding, TypeContraint.NOTEQUAL)
    }

    Constraint getNullableExtractor(def fieldScaffolding) {
        this.getConstraintDefalt(fieldScaffolding, TypeContraint.NULLABLE)
    }

    Constraint getRangeExtractor(def fieldScaffolding) {
        this.getConstraintDefalt(fieldScaffolding, TypeContraint.RANGE)
    }

    Constraint getScaleExtractor(def fieldScaffolding) {
        this.getConstraintDefalt(fieldScaffolding, TypeContraint.SCALE)
    }

    Constraint getSizeExtractor(def fieldScaffolding) {
        this.getConstraintDefalt(fieldScaffolding, TypeContraint.SIZE)
    }

    Constraint getUniqueExtractor(def fieldScaffolding) {
        this.getConstraintDefalt(fieldScaffolding, TypeContraint.UNIQUE)
    }


}
