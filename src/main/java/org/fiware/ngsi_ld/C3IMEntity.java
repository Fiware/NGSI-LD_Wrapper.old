package org.fiware.ngsi_ld;

import java.util.Map;

/**
 *
 *   A C3IM Entity
 *
 *   Copyright (c) 2017 FIWARE Foundation e.V.
 *
 *   LICENSE: MIT
 *
 *
 */
public interface C3IMEntity extends C3IMObject {
    public String getId();
    public String getType();

    public Map<String,String> jsonLdContext();
    public void jsonLdContext(Map<String,String> context);
}
