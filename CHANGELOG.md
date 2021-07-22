Change Log
==========

Version 2.3 *(2021-07-22)*
----------------------------

* Migration from jcenter to maven central  
  The group was rename from `com.nicolasmouchel` to `fr.sagix`

Version 2.2 *(2018-10-27)*
----------------------------

* Add a comment in each generated class to know the source
* Add incremental annotation flag
* Add android extensions to follow android lifecycle
  * `com.nicolasmouchel:executordecorator-android-arch-extension`
  * `com.nicolasmouchel:executordecorator-android-extension`

Version 2.1 *(2017-07-24)*
----------------------------

* Fix interfaces with inheritance

Version 2.0 *(2017-01-24)*
----------------------------

* Replace `ExecutorDecorator` annotation by three new annotations:
    * `ImmutableExecutorDecorator` replace `ExecutorDecorator`
    * `MutableExecutorDecorator` replace `ExecutorDecorator(mutable = true)`
    * `WeakExecutorDecorator` is _new_
* Remove black list of methods that `ExecutorDecorator` should not implement by listing from the interface all the
  method that should be implemented
 * Add sample java project
 * Divide implementation of `Processor` with small "`Generator`s"


Version 1.1 *(2016-12-13)*
----------------------------

 * Ignore `clone` and `finalize` method to work with __Kotlin__
 
Version 1.0 *(2016-06-28)*
----------------------------

 Initial release.