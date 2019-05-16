Change Log
==========

Version 2.2 *(2019-05-17)*
----------------------------

 * Add extensions to follow android lifecycle 

Version 2.1 *(2017-07-24)*
----------------------------

 * Fix interfaces with inheritance 

Version 2.0 *(2017-01-24)*
----------------------------

 * Replace `ExecutorDecorator` annotation by three new annotations:
    * `ImmutableExecutorDecorator` replace `ExecutorDecorator`
    * `MutableExecutorDecorator` replace `ExecutorDecorator(mutable = true)`
    * `WeakExecutorDecorator` is _new_
 * Remove black list of methods that `ExecutorDecorator` should not implement by listing from the interface all the method that should be implemented
 * Add sample java project
 * Divide implementation of `Processor` with small "`Generator`s"


Version 1.1 *(2016-12-13)*
----------------------------

 * Ignore `clone` and `finalize` method to work with __Kotlin__
 
Version 1.0 *(2016-06-28)*
----------------------------

 Initial release.