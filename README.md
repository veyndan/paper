Store and access data with the semantic web ideology. Use Apache Jena and note all the
capabilities it exposes (e.g. queries over networks). Look into limitations of not using
SQLite on Android e.g. interfacing with other apps on the system (like the infamous dictionary
example). This shouldn't be a problem as I don't plan on integrating with other apps for the time
being (if ever). I could always see if I can convert between SQL and RDF so locally I store
as SQL and do SQL queries but over the network I do RDF queries. Still no idea for the use case
of interfacing with other apps so this may be pointless. Store as RDF on device for now.
A major benefit to using RDF is that I can open source the data easily and make it useful for
people. Data that is considered private should be taken into account later. Still have no idea
on how I'm going to make money off this.

I kind of like the idea of a mutating SPARQL query (based on SPARQL parameters) on the backend as it is easy to save the filters as well there.
SPARQL parameters: https://blog.ldodds.com/2005/11/07/parameterised-queries-with-sparql-and-arq/

TODO May be preferable to instead of passing json into GET to just use parameters. People say POST requests and stuff but I think it'd be nice to be able to share a link with a friend
with some filters and sorts. I don't believe that you can do that in a POST as you aren't copying the POST body and giving it to friends.
Basically this is instead of doing Query -> Json -> Query -> SPARQL we are doing Query -> URL params -> Query -> SPARQL. No more conversion steps. Would be good to pass raw SPARQL query over network though
or pass raw SPARQL query params over network for some default SPARQL script. Could use my own Query DSL which returns the SPARQL params/SPARQL query and send it over network.
Update: I don't want SPARQL queries in the app. I only want them on the server. Use GET params and these can be the params to a SPARQL script contained on the server.
Thought: Don't use a QueryMap but set defaults for each query inside the function using default arguments.

If I continue to use JSON prefer Protocol Buffers instead.

Use prolog

Instead of the name "Manifestation" it could instead be called something relating to "Sustainable Resources" like wind power. This is because generating resources like strings, AndroidManifest, etc. is
more sustainable than hand doing it. Note that I think that strings should also be generated. Basically make everything defined in xml be generated, except from layouts which are handled
by Anko.

http://www.swi-prolog.org/pldoc/man?section=clp
http://www.swi-prolog.org/web/index.html
http://watson.kmi.open.ac.uk/REST_API.html

Spend a long time trying to find the best Rest API design. For example, though pagination is OK, maybe there's a more modern approach that has a lot of pros.
Remember that most API designs out there that I've used are like that because they are probably legacy. As I am starting fresh, it is good to find the most up to date one.
Also one that easily allows forward migration would be good as well. This means the API should abstract away the internals of my server completely, exposing an API "paradigm" (e.g.
reactive extensions is a well defined paradigm with a defined interface, the implementation of the interface should be able to change over time).

Look into MVI a LOT more:
https://github.com/bufferapp/android-clean-architecture-mvi-boilerplate
