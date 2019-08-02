package com.themecleanflex.models;

import com.peregrine.adaption.PerPage;
import com.peregrine.adaption.PerPageManager;
import com.peregrine.nodetypes.models.AbstractComponent;
import com.peregrine.nodetypes.models.IComponent;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/*
    //GEN[:DATA
    {
  "definitions": {
    "Navigation": {
      "type": "object",
      "x-type": "component",
      "properties": {
        "text": {
          "type": "string",
          "x-source": "inject",
          "x-form-type": "texteditor"
        }
      }
    }
  },
  "name": "Navigation",
  "componentPath": "themecleanflex/components/navigation",
  "package": "com.themecleanflex.models",
  "modelName": "Navigation",
  "classNameParent": "AbstractComponent"
}
//GEN]
*/

//GEN[:DEF
@Model(
        adaptables = Resource.class,
        resourceType = "themecleanflex/components/navigation",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
        adapters = IComponent.class
)
@Exporter(
        name = "jackson",
        extensions = "json"
)

//GEN]
public class PagelistModel extends AbstractComponent {

    public PagelistModel(Resource r) { super(r); }

    //GEN[:INJECT
    	/* {"type":"string","x-source":"inject","x-form-type":"texteditor"} */
	@Inject
	private String text;


//GEN]

    //GEN[:GETTERS
    	/* {"type":"string","x-source":"inject","x-form-type":"texteditor"} */
	public String getText() {
		return text;
	}


//GEN]

    //GEN[:CUSTOMGETTERS
	private static final Logger LOG = LoggerFactory.getLogger(PagelistModel.class);

	/* {"type":"string","x-source":"inject","x-form-type":"number","x-form-label":"Levels","x-form-default":1,"x-form-min":1} */
	public String getLevels() {
		return levels == null ? "1" : levels;
	}

	public String getRootPageTitle() {
		PerPageManager ppm = getResource().getResourceResolver().adaptTo(PerPageManager.class);
		PerPage page = ppm.getPage(getRootpage());
        if(page == null) return "not adaptable";
        return page != null ? page.getTitle(): "";
	}

	public String getRootPageLink() {
		PerPageManager ppm = getResource().getResourceResolver().adaptTo(PerPageManager.class);
		PerPage page = ppm.getPage(getRootpage());
        if(page == null) return "not adaptable";
        return page != null ? page.getPath(): "";
	}

	public List<Page> getChildrenPages() {
		List<Page> childPages = new ArrayList<Page>();
		String rootPage = getRootpage();
		if (rootPage != null) {
			int levels = Integer.parseInt(getLevels());
			PerPageManager ppm = getResource().getResourceResolver().adaptTo(PerPageManager.class);
			PerPage page = ppm.getPage(getRootpage());
			if (page != null) {
				for (PerPage child : page.listChildren()) {
					if (!child.getPath().equals(page.getPath())) {
						childPages.add(new Page(child, levels));
					}
				}
			}
		}
		return childPages;
	}

	class Page {

	private PerPage page;
	private int levels;

	public Page(PerPage page) {
		this.page = page;
	}

	public Page(PerPage page, int levels) {
		this.page = page;
		this.levels = levels;
	}

	public String getTitle() {
		return page.getTitle();
	}

	public String getPath() {
		return page.getPath();
	}

	public int getLevels() {
		return levels;
	}

	public Boolean getHasChildren() {
		return levels <= 1 ? false : true;
	}

	public List<Page> getChildrenPages() {
		List<Page> childPages = new ArrayList<Page>();
		System.out.println();
		if(page != null) {
			for (PerPage child: page.listChildren()) {
				if(!child.getPath().equals(page.getPath())) {
					childPages.add(new Page(child, levels-1));
				}
			}
		}
		return childPages;
	}
}
    //GEN]

}
