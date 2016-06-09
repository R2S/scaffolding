package biz.r2s.scaffolding.extractor.scaffolding;

import java.util.Map;

import biz.r2s.scaffolding.meta.TitleScaffold;
import biz.r2s.scaffolding.meta.datatatable.DatatableScaffold;
import biz.r2s.scaffolding.meta.datatatable.OrderDatatable;

/**
 * Created by raphael on 06/08/15.
 */
public class DataTableScaffoldingExtrator {

    TitleScaffoldingExtrator titleScaffoldingExtrator;
    ColumnsScaffoldingExtrator columnsScaffoldingExtrator;

    public DataTableScaffoldingExtrator() {
        titleScaffoldingExtrator = new TitleScaffoldingExtrator();
        columnsScaffoldingExtrator = new ColumnsScaffoldingExtrator();
    }

    void changeDatatable(Map datatableMap, DatatableScaffold datatableScaffold) {
        this.changeTitle(datatableMap, datatableScaffold);
        this.changePaginate(datatableMap, datatableScaffold);
        this.changeSearchable(datatableMap, datatableScaffold);
        this.changeOrder(datatableMap, datatableScaffold);
        this.changeSort(datatableMap, datatableScaffold);
        this.changeNumMaxPaginate(datatableMap, datatableScaffold);
        this.changeColumns(datatableMap, datatableScaffold);
    }

    void changePaginate(Map datatableMap, DatatableScaffold datatableScaffold) {
        Boolean value = (boolean) datatableMap.get("pagination");
        if (value!=null) {
            datatableScaffold.setPagination(value);
        }
    }

    void changeSearchable(Map datatableMap, DatatableScaffold datatableScaffold) {
    	Boolean value = (Boolean) datatableMap.get("searchable");
        if (value!=null) {
            datatableScaffold.setSearchable(value);
        }
    }

    void changeOrder(Map datatableMap, DatatableScaffold datatableScaffold) {
        String value = (String) datatableMap.get("order");
        if (value!=null) {
            datatableScaffold.setOrdenate(true);
            datatableScaffold.setOrder(value == "desc" ? OrderDatatable.DESC : OrderDatatable.ASC);
        }
    }

    void changeSort(Map datatableMap, DatatableScaffold datatableScaffold) {
        String value = (String) datatableMap.get("sort");
        if (value!=null) {
            datatableScaffold.setSort(value);
        }
    }

    void changeTitle(Map datatableMap, DatatableScaffold datatableScaffold) {
        TitleScaffold titleScaffold = titleScaffoldingExtrator.getTitle(datatableMap);
        if (titleScaffold!=null) {
            datatableScaffold.setTitle(titleScaffold);
        }
    }

    void changeNumMaxPaginate(Map datatableMap, DatatableScaffold datatableScaffold) {
        Integer value = (Integer) datatableMap.get("numMaxPaginate");
        if (value!=null) {
            datatableScaffold.setNumMaxPaginate(value);
        }
    }

    void changeColumns(Map datatableMap, DatatableScaffold datatableScaffold) {
        columnsScaffoldingExtrator.changeColumns(datatableMap, datatableScaffold);
    }
}
