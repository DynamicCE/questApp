package com.erkan.questApp.core.utilities.results;
import java.util.Optional;

public
class DataResult<T> extends Result {
    private Optional<T> optionalData;
    private T data;

    public
    DataResult ( Optional<T> optionalData, boolean success, String message ) {
        super ( success, message );
        this.optionalData = optionalData;
    }

    public
    DataResult ( T data, boolean success, String message ) {
        super ( success, message );
        this.data = data;
    }

    public
    DataResult ( Optional<T> data, boolean success ) {
        super ( success );
        this.optionalData = optionalData;
    }

    public
    T getData () {
        return data;
    }

    public
    Optional<T> getOptionalData () {
        return this.optionalData;
    }
}
