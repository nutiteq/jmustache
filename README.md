
This fork of jmustache is created to port https://github.com/samskivert/jmustache for CLDC 
1.1 platforms. They have old Java (Java 1.1) with many limitations like missing Map, 
Collections etc. It is specifically created to support Mustache templates as used in MBTiles map
packages in RIM BlackBerry devices. The result package is open source mapper app
supporting MBTiles and UTFGrid tooltips, get it from 
https://bitbucket.org/nutiteq/blackberry-map-samples or from BlackBerry App World.

Additional package AndroidME is used, it provides a port of some
needed newer Java features (Map, Iteration etc) for J2ME/CLDC. The package is modified for BlackBerry,
mostly by renaming packages to remove possible conflict with reserved com.java etc packages. AndroidME
includes Javolution. They are all free Apache-licensed packages.

Limitations
======
  * Maven configurations are not tested
  * JAR is generated manually (with Eclipse Export as JAR file)
  * JAR needs to be preverified with BB SDK preverify.exe, Verification Errors are expected otherwise.
  * Rudimetary unit tests are working and TestRunner.java needs manual running as Java app
  * Tested only with FastMap context and with simple templates.
  * Not tested with J2ME midlets (but should work in theory), used for BB RIM app only.


Usage
=====

Easiest would be to take mustache_bb.jar file from project root, this is precompiled and verified with 
BB preverifier. Linking of source project directly in Eclipse did not work for me, ended with RIM nice
Preverification errors, best friends of every BB developer.

Using JMustache is very simple. Supply your template as a `String` or a
`Reader` and get back a `Template` that you can execute on any context. I use JSON data here which is converted
to FastMap.

    String text = "One, two, {{three}}. Three sir!";
    Template tmpl = Mustache.compiler().compile(text);

    String json = "{\"three\":\"five\"}";
    JSONObject root = new JSONObject(json);

    FastMap data = new FastMap();

    JSONArray names = root.names();
    for(int i = 0; i < names.length(); i++){
        String name = names.getString(i);
        String value = root.getString(name);
        data.put(name,value);
    }

    System.out.println(tmpl.execute(data));
    // result: "One, two, five. Three sir!"

