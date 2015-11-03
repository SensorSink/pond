pond
====

SensorSink Pond is a timeseries database, optimized for sampled sensor data at Big Volume. anticipating the need for scalable sensor solution in the age of Internet of Things that are coming.

Requirements
----

* 10s of Millions of sensors per database
* Update speed (sampling rate) from a millisecond to years
* Primary Datatypes
    * Real - a floating point measurement
    * Integer - a 64 bit integer signed number
    * Count - an additive field, typically growing with the value added
    * Boolean - True/False indicator
    * String - a Text field of arbitrary size.
    * JSON - Json objects of arbitrary size.
* Apply functions to timeseries
    * Mininum, Maximum, Average and Standard Deviation over a defined time interval for a timeseries
    * Minimum, Maximum, Average and Standard Deviation on multiple timeseries, single sample within a time window.
    * Custom functions
    * Function applied to, and modifying the timeseries. Or to create new timeseries. 
* Query Language for timeseries data
    * Functions
    * Aggregation
    * Massive volumes
    * Time centric
* REST interface
    * JSON as primary representation
    * XML and BSON supported from SensorSink
    * Custom representations must be possible.
    * GZIP support
    * Multi-query aggregation into GZIP result file.


