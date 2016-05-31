package br.ufscar.sagui.scaffolding.extractor.scaffolding

import br.ufscar.sagui.scaffolding.meta.TitleScaffold

/**
 * Created by raphael on 06/08/15.
 */
class TitleScaffoldingExtrator {
    TitleScaffold getTitle(def scaffolding){
        TitleScaffold titleScaffold = null
        def title = scaffolding.get("title")

        if(title){
            titleScaffold = new TitleScaffold()
            if(title instanceof String){
                titleScaffold.name = title
            }else{
                titleScaffold.name = title.title
                titleScaffold.name = title.subTitle
            }
        }
        return titleScaffold
    }
}
