package gr.snika.diorasi.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum SearchFilter {
	
	DEVICE("device"),
	
	BROWSER("browser"),
	
	OPERATING_SYSTEM("os"),
	
	COUNTRY("coutry"),
	
	RESOLUTION("resolution");
	
	private String name;
	
	private static final Map<String, SearchFilter> ENUM_MAP;
	
	SearchFilter(String name) {
		this.name = name;
	}
	
	public String getName() {
        return this.name;
    }
	
	static {
        Map<String, SearchFilter> map = new ConcurrentHashMap<String, SearchFilter>();
        for (SearchFilter instance : SearchFilter.values()) {
            map.put(instance.getName().toLowerCase(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static SearchFilter get (String name) {
        return ENUM_MAP.get(name.toLowerCase());
    }
}
