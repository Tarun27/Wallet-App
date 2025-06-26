Logging approach

1. Controller
    * Log only at DEBUG (or INFO for very high-value endpoints).
    * Focus on HTTP metadata—method, path, status, latency—and validation errors.
2. Service
    * Your “heartbeat” logs live here. Use INFO for start/end of each business operation.
    * Parameterize messages so you can filter/aggregate by operation name.
    * Log branch decisions (e.g. “User exists → sending reset link” vs “User not found → returning 404”).
3. Repository
    * Let your connection pool / ORM generate SQL-level logs at TRACE if you’re debugging.
    * In your own code, log only when something goes sideways: slow queries (exceeding X ms) or exceptions.
4. Exceptions
    * Always log the exception’s stack trace at the layer that handles it (usually service or controller).
    * Don’t double-log at every layer—catch, handle, or rethrow once and log there.
5. Use AOP sparingly
    * A “heartbeat” aspect on services is great for consistent entry/exit logs.
    * Don’t use it to log controllers or repos at INFO level—stick to method-entry/exit at DEBUG if you must.

I’ve provided a turnkey “tiered logging” setup:
                         
1. HTTP Logging via HttpLoggingInterceptor (DEBUG-level metadata in controllers)
2. Service Heartbeats via LoggingAspect (INFO-level entry/exit/errors)
3. Repo Timing via RepositoryPerformanceAspect (WARN if queries exceed a threshold)
4. Global Error Handler GlobalExceptionHandler (ERROR on unhandled exceptions)
5. Config in application.yml for your slow-query threshold


TODO:
                                                
1. logback-spring.xml for your core config
    * Lets you define rich appenders, patterns, and <springProfile> blocks
    * You get profile-aware logging (DEV vs PROD) without code changes
    * You can include MDC keys, switch JSON vs text output, hook in Splunk/Hadoop appenders, etc.
2. Spring Boot Actuator’s “loggers” endpoint for on-the-fly tweaks
    * Flip any package or logger to DEBUG/TRACE at runtime—no restart, no redeploy
    * Perfect for hot-fixing problems in production or giving devs deeper insights in staging

Plus you still keep application.yml (or application-{profile}.yml) for your defaults, but delegate the heavy lifting to logback-spring and the actuator.

With that combo you’ll have:

* Clear, environment-specific defaults (via <springProfile> blocks)
* Runtime flexibility (via Actuator)
* Full control over format, appenders, and enrichment (via logback-spring)

