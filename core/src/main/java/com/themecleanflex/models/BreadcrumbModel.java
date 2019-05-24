package com.themecleanflex.models;

import com.peregrine.nodetypes.merge.PageMerge;
import com.peregrine.nodetypes.models.AbstractComponent;
import com.peregrine.nodetypes.models.IComponent;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;

import javax.inject.Inject;

/*
    //GEN[:DATA
    {
  "definitions": {
    "Breadcrumb": {
      "type": "object",
      "x-type": "component",
      "properties": {
        "level": {
          "type": "string",
          "x-source": "inject",
          "x-form-label": "Number Of Levels",
          "x-form-type": "number"
        }
      }
    }
  },
  "name": "Breadcrumb",
  "componentPath": "themecleanflex/components/breadcrumb",
  "package": "com.themecleanflex.models",
  "modelName": "Breadcrumb",
  "classNameParent": "AbstractComponent"
}
//GEN]
*/

//GEN[:DEF
@Model(
        adaptables = Resource.class,
        resourceType = "themecleanflex/components/breadcrumb",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
        adapters = IComponent.class
)
@Exporter(
        name = "jackson",
        extensions = "json"
)

//GEN]
public class BreadcrumbModel extends AbstractComponent {
  
	public BreadcrumbModel(Resource r) { super(r); }

    //GEN[:INJECT
    	/* {"type":"string","x-source":"inject","x-form-label":"Number Of Levels","x-form-type":"number"} */
	@Inject
	private String level;


//GEN]

    //GEN[:GETTERS
    	/* {"type":"string","x-source":"inject","x-form-label":"Number Of Levels","x-form-type":"number"} */
	public String getLevel() {
		return level;
	}


//GEN]

    //GEN[:CUSTOMGETTERS
    //GEN]
	private static final Logger LOG = LoggerFactory.getLogger(BreadcrumbModel.class);
	
	public List<TextLink> links;
	
	/* Method to recursively get child page links, given a root page path */
    public List<TextLink> getLinks(){
      
    	links = new ArrayList<TextLink>();
    	if(Integer.parseInt(getLevel()) > 0) {
        Resource page = getRootResource();
        LOG.debug("page: {}", page);
    		return getDeepLinks(page != null ? page : getResource());
    	} else {
    		return null;
    	}
    	
    }
    
    private List<TextLink> getDeepLinks(Resource resource){

    	try{
			    		
    		ValueMap props = resource.adaptTo(ValueMap.class);
		    String resourceType = props.get("jcr:primaryType", "type not found");
		    // we only care about per:page child
		    if(resourceType.equals("per:Page")){
			    TextLink link = new TextLink(resource.getPath(), getPageTitle(resource.getPath()));
			    links.add(0,link);
		    }
		    // move on to its parent resource
		    if(resource.getParent() != null && links.size() < Integer.parseInt(getLevel())) {
		    	getDeepLinks(resource.getParent());
		    }
    	} catch(Exception e){
    		LOG.error("getDeepLinks error: {}",e);
		}
    	
    	return links;
    	
    	
    }
    	
	private String getPageTitle(String pageUrl){
		try{
			String resourcePath = pageUrl + "/jcr:content";
			ResourceResolver resourceResolver = getResource().getResourceResolver();
			ValueMap props = resourceResolver.getResource(resourcePath).adaptTo(ValueMap.class);
			return props.get("jcr:title", "title not found");
		} catch(Exception e){
			LOG.error("getPageTitle error: {}",e);
			return "title not found....";
		}
	}
    
	private class TextLink {
		
		public TextLink(String link, String text){
			this.link = link;
			this.text = text;
		}
		private String link;
		private String text;
		
		public String getLink(){
			return link;
		}
		
		public String getText(){
			return text;
		}
	}

}
