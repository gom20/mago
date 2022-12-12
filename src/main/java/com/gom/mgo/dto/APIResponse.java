package com.gom.mgo.dto;

public class APIResponse<T> {
	private final T data;
	
    private APIResponse(T data){
        this.data = data;
    }

    public static <T> APIResponse<T> of(T data) {
        return new APIResponse(data);
    }
}
