package com.erkan.questApp.core.utilities.results;


import java.util.Optional;

public
class SuccessDataResult<T> extends DataResult<T> {
    public
    SuccessDataResult ( T data, String message ) {
        super ( data, true, message );
    }

    public
    SuccessDataResult ( Optional<T> data, String message ) {
        super ( data, true, message );
    }

    public
    SuccessDataResult ( Optional<T> data ) {
        super ( data, true );
    }

    public
    SuccessDataResult ( String message ) {
        super ( Optional.empty ( ), true, message );
    }

    public
    SuccessDataResult () {
        super ( Optional.empty ( ), true );
    }
}