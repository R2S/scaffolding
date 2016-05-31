package br.ufscar.sagui.scaffolding.extractor.scaffolding

import br.ufscar.sagui.scaffolding.meta.datatatable.CampoDatatable
import br.ufscar.sagui.scaffolding.meta.datatatable.DatatableScaffold

/**
 * Created by raphael on 06/08/15.
 */
class ColumnsScaffoldingExtrator {

    IconScaffoldingExtrator iconScaffoldingExtrator

    public ColumnsScaffoldingExtrator(){
        iconScaffoldingExtrator = new IconScaffoldingExtrator()
    }

    void changeColumns(def datatableMap, DatatableScaffold datatableScaffold){
        def value = datatableMap.get("columns")
        if(value){
            int count = 0
            List<CampoDatatable> campoDatatables = []
            if(value instanceof List){
                value.each {String nameColumn ->
                    count++
                    CampoDatatable campoDatatable = this.getColumnsDefalt(nameColumn, datatableScaffold)
                    campoDatatable.order = count
                    campoDatatables << campoDatatable
                }
                datatableScaffold
            }else{
                value.each{String nameColumn, campoScaffolding ->
                    CampoDatatable campoDatatable = this.getColumnsDefalt(nameColumn, datatableScaffold)
                    count++
                    if(campoScaffolding instanceof String){
                        campoDatatable.title = campoScaffolding
                    }else{
                        this.changeTitle(campoScaffolding, campoDatatable)
                        this.changeIcon(campoScaffolding, campoDatatable)
                        this.changeLength(campoScaffolding, campoDatatable)
                    }
                    campoDatatable.order = count
                    campoDatatables << campoDatatable
                }
            }
            datatableScaffold.columns  = campoDatatables
        }
    }

    CampoDatatable getColumnsDefalt(String name,DatatableScaffold datatableScaffold){
        CampoDatatable campoDatatable = new CampoDatatable()
        campoDatatable.title = name
        campoDatatable.key = name
        campoDatatable.name = name
        campoDatatable.parent = datatableScaffold
        return campoDatatable
    }

    void changeTitle(def campoScaffolding, CampoDatatable campoDatatable){
        def value = campoScaffolding.get("title")
        if(value){
            campoDatatable.title = value
        }
    }

    void changeLength(def campoScaffolding, CampoDatatable campoDatatable){
        def value = campoScaffolding.get("length")
        if(value){
            campoDatatable.length = value
        }
    }

    void changeIcon(def campoScaffolding, CampoDatatable campoDatatable){
        def icon = iconScaffoldingExtrator.getIcon(campoScaffolding)
        if(icon){
            campoScaffolding.icon = icon
        }
    }

}
