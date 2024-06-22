package com.erkan.questApp.core.utilities.results;

import java.util.Optional;

public
class ErrorDataResult<T> extends DataResult<T> {
    public
    ErrorDataResult ( T data, String message ) {
        super ( data, false, message );
    }

    public
    ErrorDataResult ( Optional<T> data, String message ) {
        super ( data, false, message );
    }

    public
    ErrorDataResult ( Optional<T> data ) {
        super ( data, false );
    }

    public
    ErrorDataResult ( String message ) {
        super ( Optional.empty ( ), false, message );
    }

    public
    ErrorDataResult () {
        super ( Optional.empty ( ), false );
    }
}

