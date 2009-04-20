package com.izhi.web.util;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class MapAdapter extends XmlAdapter<MapPair[],Map<Object, Object>>{
	   
    public Map<Object, Object> unmarshal(MapPair[] pairs) {
        Map<Object,Object> map = new HashMap<Object, Object>();
        for (MapPair pair : pairs) {
            map.put(pair.key, pair.value);
        }
        return map;
    }

    public MapPair[] marshal(Map<Object, Object> v) throws Exception {
        MapPair[] pairs = new MapPair[v.size()];
        int i = 0;
        for(Map.Entry<Object, Object> entry : v.entrySet()) {
            pairs[i++] = new MapPair(entry.getKey().toString(), entry.getValue().toString());
        }
        return pairs;
    }
}
