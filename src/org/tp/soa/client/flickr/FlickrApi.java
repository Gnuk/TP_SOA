package org.tp.soa.client.flickr;

import javax.xml.ws.WebServiceRef;
public class FlickrApi {
	@WebServiceRef(wsdlLocation="http://api.flickr.com/services/soap/")
	
	/*
		flickr.photos.search
	 */
/*
<s:Envelope
	xmlns:s="http://www.w3.org/2003/05/soap-envelope"
	xmlns:xsi="http://www.w3.org/1999/XMLSchema-instance"
	xmlns:xsd="http://www.w3.org/1999/XMLSchema"
>
	<s:Body>
		<x:FlickrRequest xmlns:x="urn:flickr">
			<method>flickr.photos.search</method>
			<tag>value</tag>
		</x:FlickrRequest>
	</s:Body>
</s:Envelope>
*/
	/**
	 * Récuèpre le WSDL par rapport au tag
	 * @param tag
	 * @return
	 */
	public String searchFromTag(String tag){
		return null;
	}
}
