package br.ufscar.sagui.scaffolding.mapping

import br.ufscar.sagui.util.GrailsUtil

/**
 * Created by raphael on 13/08/15.
 */
class MappingGrails {
    def getGrailsUrlMappingsHolder() {
        return GrailsUtil.getGrailsApplication().mainContext.grailsUrlMappingsHolder
    }

    def addMapping(String url, String resource, Class resourceClass, String phm, String controller) {
        this.getGrailsUrlMappingsHolder().addMappings({
            "${url}/(*)/descendentes"(controller: controller, action: "descendentes", method: "GET"){
                resourceName = resourceClass.name
                propertyHasMany = phm
            }
            "${url}/(*)/ascendentes"(controller:controller, action: "ascendentes", method: "GET"){
                resourceName = resourceClass.name
                propertyHasMany = phm
            }
            "${url}/(*)/children"(controller:controller, action: "children", method: "GET"){
                resourceName = resourceClass.name
                propertyHasMany = phm
            }
            "${url}/(*)"(controller:controller, action: "update", method: "POST"){
                resourceName = resourceClass.name
                propertyHasMany = phm
            }
            "${url}"(resources:resource){
                resourceName = resourceClass.name
                propertyHasMany = phm
            }
            "${url}/meta/list"(controller:"meta", action:"list"){
                resourceName = resourceClass.name
                propertyHasMany = phm
            }
            "${url}/meta/create"(controller:"meta", action:"create"){
                resourceName = resourceClass.name
                propertyHasMany = phm
            }
            "${url}/meta/edit"(controller:"meta", action:"edit"){
                resourceName = resourceClass.name
                propertyHasMany = phm
            }
            "${url}/meta/show"(controller:"meta", action:"show"){
                resourceName = resourceClass.name
                propertyHasMany = phm
            }
        })
    }


}
