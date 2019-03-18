package com.aniket.homework.componentservice.util;

public class Constants {

    public static final String VERSION = "version";
    public static final String WORKSPACE_ID = "workspaceId";
    public static final String ENVIRONMENT_ID = "environmentId";
    public static final String SOURCE_REPOSITORY_ID = "sourceRepositoryId";
    public static final String DATABASE_ID = "databaseId";

    public enum COMPONENTTYPE {
        WORKSPACE("WORKSPACE"),
        ENVIRONMENT("ENVIRONMENT"),
        SOURCE_REPOSITORY("SOURCE_REPOSITORY"),
        DATABASE("DATABASE"),
        INVALID_COMPONENTTYPE("INVALID_COMPONENTTYPE");

        private final String value;
        COMPONENTTYPE(String v){ value = v;}
        public static COMPONENTTYPE fromValue(String v) {
            try {
                for (COMPONENTTYPE c : COMPONENTTYPE.values()) {
                    if (c.value.equalsIgnoreCase(v))
                        return c;
                }
            } catch(Exception ex){
                return INVALID_COMPONENTTYPE;
            }
            return INVALID_COMPONENTTYPE;
        }
    }
}
