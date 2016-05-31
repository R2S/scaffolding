package br.ufscar.sagui.scaffolding.format

import br.ufscar.sagui.scaffolding.RulesFacade
import br.ufscar.sagui.scaffolding.meta.ClassScaffold
import br.ufscar.sagui.scaffolding.meta.ResourceUrlScaffold
import br.ufscar.sagui.scaffolding.meta.action.TypeActionScaffold
import br.ufscar.sagui.scaffolding.meta.datatatable.CampoDatatable
import br.ufscar.sagui.scaffolding.meta.datatatable.DatatableScaffold
import br.ufscar.sagui.scaffolding.meta.datatatable.OrderDatatable
import br.ufscar.sagui.scaffolding.meta.field.TypeFieldScaffold
import br.ufscar.sagui.scaffolding.security.PermissionFacade

/**
 * Created by raphael on 11/08/15.
 */
class DatatableFormat {

    PermissionFacade permissionFacade
    CommonFormat commonFormat

    public DatatableFormat() {
        commonFormat = new CommonFormat()
        permissionFacade = new PermissionFacade()
    }

    def formatarDatatable(def permission, DatatableScaffold dt, def fatherId) {
        def meta = [:]
        meta.pagination = dt.pagination
        meta.searchable = dt.searchable
        meta.ordenate = dt.ordenate
        meta.sortable = dt.sortable
        if (dt.order) {
            meta.order = dt.order == OrderDatatable.DESC ? "desc" : "asc"
        }
        meta.sort = dt.sort
        meta.title = commonFormat.formatTitle(dt.title)
        meta.numPaginate = dt.numMaxPaginate
        meta.url = this.formatUrlDataTable(dt.resourceUrlScaffold, fatherId)
        meta.columns = this.formatColumns(permission, dt.columns, dt.getClassScaffold())
        return meta
    }

    def formatColumns(def permission, List<CampoDatatable> columns, ClassScaffold ClassScaffold) {
        def columnsMeta = []

        RulesFacade.instance.listColumns(permission, columns)?.each {
            columnsMeta << formatColumn(it)
        }

        return columnsMeta
    }

    def formatColumn(CampoDatatable it) {
        def columnMeta = [name: it.name, title: it.title, key: it.key, length: it.length, type:getType(it).name(), order:it.order]
        if (it.icon) {
            columnMeta.icon = commonFormat.formatIcon(it.icon)
        }
        columnMeta
    }

    def getType(CampoDatatable campoDatatable){
        campoDatatable.parent.getClassScaffold()?.fields?.find({it.key==campoDatatable.name})?.type?:TypeFieldScaffold.INPUT
    }

    def formatUrlDataTable(ResourceUrlScaffold resourceUrlScaffold, def fatherId) {
        return resourceUrlScaffold.resolver(TypeActionScaffold.LIST, fatherId).formatUrl()
    }
}
