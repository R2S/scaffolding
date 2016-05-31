package br.ufscar.sagui.scaffolding.extractor.scaffolding

import br.ufscar.sagui.scaffolding.meta.TitleScaffold
import br.ufscar.sagui.scaffolding.meta.datatatable.DatatableScaffold
import br.ufscar.sagui.scaffolding.meta.datatatable.OrderDatatable

/**
 * Created by raphael on 06/08/15.
 */
class DataTableScaffoldingExtrator {

    TitleScaffoldingExtrator titleScaffoldingExtrator
    ColumnsScaffoldingExtrator columnsScaffoldingExtrator

    public DataTableScaffoldingExtrator() {
        titleScaffoldingExtrator = new TitleScaffoldingExtrator()
        columnsScaffoldingExtrator = new ColumnsScaffoldingExtrator()
    }

    void changeDatatable(def datatableMap, DatatableScaffold datatableScaffold) {
        this.changeTitle(datatableMap, datatableScaffold)
        this.changePaginate(datatableMap, datatableScaffold)
        this.changeSearchable(datatableMap, datatableScaffold)
        this.changeOrder(datatableMap, datatableScaffold)
        this.changeSort(datatableMap, datatableScaffold)
        this.changeNumMaxPaginate(datatableMap, datatableScaffold)
        this.changeColumns(datatableMap, datatableScaffold)
    }

    void changePaginate(def datatableMap, DatatableScaffold datatableScaffold) {
        def value = datatableMap.get("pagination")
        if (value!=null) {
            datatableScaffold.pagination = value
        }
    }

    void changeSearchable(def datatableMap, DatatableScaffold datatableScaffold) {
        def value = datatableMap.get("searchable")
        if (value!=null) {
            datatableScaffold.searchable = value
        }
    }

    void changeOrder(def datatableMap, DatatableScaffold datatableScaffold) {
        def value = datatableMap.get("order")
        if (value) {
            datatableScaffold.ordenate = true
            datatableScaffold.order = value == "desc" ? OrderDatatable.DESC : OrderDatatable.ASC
        }
    }

    void changeSort(def datatableMap, DatatableScaffold datatableScaffold) {
        def value = datatableMap.get("sort")
        if (value) {
            datatableScaffold.sort = value
        }
    }

    void changeTitle(def datatableMap, DatatableScaffold datatableScaffold) {
        TitleScaffold titleScaffold = titleScaffoldingExtrator.getTitle(datatableMap)
        if (titleScaffold) {
            datatableScaffold.title = titleScaffold
        }
    }

    void changeNumMaxPaginate(def datatableMap, DatatableScaffold datatableScaffold) {
        def value = datatableMap.get("numMaxPaginate")
        if (value) {
            datatableScaffold.numMaxPaginate = value
        }
    }

    void changeColumns(def datatableMap, DatatableScaffold datatableScaffold) {
        columnsScaffoldingExtrator.changeColumns(datatableMap, datatableScaffold)
    }
}
