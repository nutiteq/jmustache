//
// JMustache - A Java implementation of the Mustache templating language
// http://github.com/samskivert/jmustache/blob/master/LICENSE

package com.samskivert.mustache;

import com.samskivert.mustache.Mustache;
//import com.samskivert.mustache.Template;

import j2me.java.util.Map;
import javolution.util.FastMap;

/**
 * Various unit tests.
 */
public class MustacheTest
{
    public void testSimpleVariable () {
        test("bar", "{{foo}}", context("foo", "bar"));
    }
/*
	public void testFieldVariable () {
        test("bar", "{{foo}}", new Object() {
            String foo = "bar";
        });
    }

    public void testMethodVariable () {
        test("bar", "{{foo}}", new Object() {
            String foo () { return "bar"; }
        });
    }

    public void testPropertyVariable () {
        test("bar", "{{foo}}", new Object() {
            String getFoo () { return "bar"; }
        });
    }

    public void testSkipVoidReturn () {
        test("bar", "{{foo}}", new Object() {
            void foo () {}
            String getFoo () { return "bar"; }
        });
    }
*/
    /*
    public void testPrimitiveArrayVariable () {
        test("1234", "{{#foo}}{{this}}{{/foo}}", new Object() {
            int[] getFoo () { return new int[] { 1, 2, 3, 4 }; }
        });
    }
    */
/*
    public void testCallSiteReuse () {
        Template tmpl = Mustache.compiler().compile("{{foo}}");
        Object ctx = new Object() {
            String getFoo () { return "bar"; }
        };
        for (int ii = 0; ii < 50; ii++) {
            assertEquals("bar", tmpl.execute(ctx));
        }
    }

    public void testCallSiteChange () {
        Template tmpl = Mustache.compiler().compile("{{foo}}");
        assertEquals("bar", tmpl.execute(new Object() {
            String getFoo () { return "bar"; }
        }));
        assertEquals("bar", tmpl.execute(new Object() {
            String foo = "bar";
        }));
    }
*/
    public void testOneShotSection () {
        test("baz", "{{#foo}}{{bar}}{{/foo}}{{#other}}{{bar}}{{/other}}", context("foo", context("bar", "baz")));
    }
/*
    public void testListSection () {
        test("bazbif", "{{#foo}}{{bar}}{{/foo}}", context(
                 "foo", Arrays.asList(context("bar", "baz"), context("bar", "bif"))));
    }

    public void testArraySection () {
        test("bazbif", "{{#foo}}{{bar}}{{/foo}}",
             context("foo", new Object[] {
                     context("bar", "baz"), context("bar", "bif") }));
    }

    public void testIteratorSection () {
        test("bazbif", "{{#foo}}{{bar}}{{/foo}}",
             context("foo", Arrays.asList(context("bar", "baz"),
                                          context("bar", "bif")).iterator()));
    }

    public void testEmptyListSection () {
        test("", "{{#foo}}{{bar}}{{/foo}}", context("foo", Collections.emptyList()));
    }

    public void testEmptyArraySection () {
        test("", "{{#foo}}{{bar}}{{/foo}}", context("foo", new Object[0]));
    }

    public void testEmptyIteratorSection () {
        test("", "{{#foo}}{{bar}}{{/foo}}", context("foo", Collections.emptyList().iterator()));
    }

    public void testFalseSection () {
        test("", "{{#foo}}{{bar}}{{/foo}}", context("foo", false));
    }

    public void testNestedListSection () {
        test("1234", "{{#a}}{{#b}}{{c}}{{/b}}{{#d}}{{e}}{{/d}}{{/a}}",
             context("a", context("b", new Object[] { context("c", "1"), context("c", "2") },
                                  "d", new Object[] { context("e", "3"), context("e", "4") })));
    }

    public void testNullSection () {
        test("", "{{#foo}}{{bar}}{{/foo}}", new Object() {
            Object foo = null;
        });
    }

    public void testNullSectionWithDefaultValue () {
        test(Mustache.compiler().defaultValue(""), "", "{{#foo}}{{bar}}{{/foo}}", new Object() {
            Object foo = null;
        });
    }

    public void testNullSectionWithNullValue () {
        test(Mustache.compiler().nullValue(""), "", "{{#foo}}{{bar}}{{/foo}}", new Object() {
            Object foo = null;
        });
    }

    public void testMissingSection () {
        test("", "{{#foo}}{{bar}}{{/foo}}", new Object() {
            // no foo
        });
    }

    public void testMissingSectionWithDefaultValue () {
        test(Mustache.compiler().defaultValue(""), "", "{{#foo}}{{bar}}{{/foo}}", new Object() {
            // no foo
        });
    }

    (expected=MustacheException.class)
    public void testMissingSectionWithNullValue () {
        test(Mustache.compiler().nullValue(""), "", "{{#foo}}{{bar}}{{/foo}}", new Object() {
            // no foo
        });
    }

    public void testComment () {
        test("foobar", "foo{{! nothing to see here}}bar", new Object());
    }

    (expected=UnsupportedOperationException.class)
    public void testPartialUseWhenUnconfigured () {
        test(null, "{{>foo}}", null);
    }

    public void testPartial () {
        test(Mustache.compiler().withLoader(new Mustache.TemplateLoader() {
            public Reader getTemplate (String name) {
                if (name.equals("foo")) {
                    return new StringReader("inside:{{bar}}");
                } else {
                    return new StringReader("nonfoo");
                }
            }
        }), "foo inside:foo nonfoo foo", "{{bar}} {{>foo}} {{>baz}} {{bar}}", context("bar", "foo"));
    }

    public void testPartialPlusNestedContext () {
        test(Mustache.compiler().withLoader(new Mustache.TemplateLoader() {
            public Reader getTemplate (String name) {
                if (name.equals("nested")) {
                    return new StringReader("{{name}}{{thing_name}}");
                } else {
                    return new StringReader("nonfoo");
                }
            }
        }), "foo((foobar)(foobaz))", "{{name}}({{#things}}({{>nested}}){{/things}})",
            context("name", "foo",
                    "things", Arrays.asList(context("thing_name", "bar"),
                                            context("thing_name", "baz"))));
    }

    public void testDelimiterChange () {
        test("foo bar baz", "{{one}} {{=<% %>=}}<%two%><%={{ }}=%> {{three}}",
             context("one", "foo", "two", "bar", "three", "baz"));
        test("baz bar foo", "{{three}} {{=% %=}}%two%%={{ }}=% {{one}}",
             context("one", "foo", "two", "bar", "three", "baz"));
    }

    public void testUnescapeHTML () {
        assertEquals("<b>", Mustache.compiler().escapeHTML(true).compile("{{&a}}").
                     execute(context("a", "<b>")));
        assertEquals("<b>", Mustache.compiler().escapeHTML(true).compile("{{{a}}}").
                     execute(context("a", "<b>")));
        // make sure these also work when escape HTML is off
        assertEquals("<b>", Mustache.compiler().escapeHTML(false).compile("{{&a}}").
                     execute(context("a", "<b>")));
        assertEquals("<b>", Mustache.compiler().escapeHTML(false).compile("{{{a}}}").
                     execute(context("a", "<b>")));
    }

    public void testDanglingTag () {
        test("foo{", "foo{", context("a", "<b>"));
        test("foo{{", "foo{{", context("a", "<b>"));
        test("foo{{a", "foo{{a", context("a", "<b>"));
        test("foo{{a}", "foo{{a}", context("a", "<b>"));
    }

    public void testStrayTagCharacters () {
        test("funny [b] business {{", "funny {{a}} business {{", context("a", "[b]"));
        test("funny <b> business {{", "funny {{{a}}} business {{", context("a", "<b>"));
        test("{{ funny [b] business", "{{ funny {{a}} business", context("a", "[b]"));
        test("{{ funny <b> business", "{{ funny {{{a}}} business", context("a", "<b>"));
        test("funny [b] business }}", "funny {{a}} business }}", context("a", "[b]"));
        test("funny <b> business }}", "funny {{{a}}} business }}", context("a", "<b>"));
        test("}} funny [b] business", "}} funny {{a}} business", context("a", "[b]"));
        test("}} funny <b> business", "}} funny {{{a}}} business", context("a", "<b>"));
    }

    (expected=MustacheParseException.class)
    public void testInvalidUnescapeHTML () {
        Mustache.compiler().escapeHTML(true).compile("{{{a}}").execute(context("a", "<b>"));
    }
    public void testEscapeHTML () {
        assertEquals("&lt;b&gt;", Mustache.compiler().compile("{{a}}").
                     execute(context("a", "<b>")));
        assertEquals("<b>", Mustache.compiler().escapeHTML(false).compile("{{a}}").
                     execute(context("a", "<b>")));
    }

    public void testPartialDelimiterMatch () {
        assertEquals("{bob}", Mustache.compiler().compile("{bob}").execute(context()));
        assertEquals("bar", Mustache.compiler().compile("{{bob}bob}}").execute(
                         context("bob}bob", "bar")));
    }

    private Object context() {
		// TODO Auto-generated method stub
		return null;
	}



	public void testTopLevelThis () {
        assertEquals("bar", Mustache.compiler().compile("{{this}}").execute("bar"));
        assertEquals("bar", Mustache.compiler().compile("{{.}}").execute("bar"));
    }

//    public void testNestedThis () {
//        assertEquals("barbazbif", Mustache.compiler().compile("{{#things}}{{this}}{{/things}}").
//                     execute(context("things", Arrays.asList("bar", "baz", "bif"))));
//        assertEquals("barbazbif", Mustache.compiler().compile("{{#things}}{{.}}{{/things}}").
//                execute(context("things", Arrays.asList("bar", "baz", "bif"))));
//    }

    public void testCompoundVariable () {
        test("hello", "{{foo.bar.baz}}", new Object() {
            Object foo () {
                return new Object() {
                    Object bar = new Object() {
                        String baz = "hello";
                    };
                };
            }
        });
    }

//    (expected=MustacheException.class)
//    public void testNullComponentInCompoundVariable () {
//        test(Mustache.compiler(), "unused", "{{foo.bar.baz}}", new Object() {
//            Object foo = new Object() {
//                Object bar = null;
//            };
//        });
//    }
//
//    (expected=MustacheException.class)
//    public void testMissingComponentInCompoundVariable () {
//        test(Mustache.compiler(), "unused", "{{foo.bar.baz}}", new Object() {
//            Object foo = new Object(); // no bar
//        });
//    }

    public void testNullComponentInCompoundVariableWithDefault () {
        test(Mustache.compiler().nullValue("null"), "null", "{{foo.bar.baz}}", new Object() {
            Object foo = null;
        });
        test(Mustache.compiler().nullValue("null"), "null", "{{foo.bar.baz}}", new Object() {
            Object foo = new Object() {
                Object bar = null;
            };
        });
    }

    public void testMissingComponentInCompoundVariableWithDefault () {
        test(Mustache.compiler().defaultValue("?"), "?", "{{foo.bar.baz}}", new Object() {
            // no foo, no bar
        });
        test(Mustache.compiler().defaultValue("?"), "?", "{{foo.bar.baz}}", new Object() {
            Object foo = new Object(); // no bar
        });
    }
/*
    public void testNewlineSkipping () {
        String tmpl = "list:\n" +
            "{{#items}}\n" +
            "{{this}}\n" +
            "{{/items}}\n" +
            "{{^items}}\n" +
            "no items\n" +
            "{{/items}}\n" +
            "endlist";
        test("list:\n" +
             "one\n" +
             "two\n" +
             "three\n" +
             "endlist", tmpl, context("items", Arrays.asList("one", "two", "three")));
        test("list:\n" +
             "no items\n" +
             "endlist", tmpl, context("items", Collections.emptyList()));
    }

    public void testNewlineNonSkipping () {
        // only when a section tag is by itself on a line should we absorb the newline following it
        String tmpl = "thing?: {{#thing}}yes{{/thing}}{{^thing}}no{{/thing}}\n" +
            "that's nice";
        test("thing?: yes\n" +
             "that's nice", tmpl, context("thing", true));
        test("thing?: no\n" +
             "that's nice", tmpl, context("thing", false));
    }

    public void testNestedContexts () {
        test("foo((foobar)(foobaz))", "{{name}}({{#things}}({{name}}{{thing_name}}){{/things}})",
             context("name", "foo",
                     "things", Arrays.asList(context("thing_name", "bar"),
                                             context("thing_name", "baz"))));
    }

    public void testShadowedContext () {
        test("foo((bar)(baz))", "{{name}}({{#things}}({{name}}){{/things}})",
             context("name", "foo",
                     "things", Arrays.asList(context("name", "bar"), context("name", "baz"))));
    }

    public void testFirst () {
        test("foo|bar|baz", "{{#things}}{{^-first}}|{{/-first}}{{this}}{{/things}}",
             context("things", Arrays.asList("foo", "bar", "baz")));
    }

    public void testLast () {
        test("foo|bar|baz", "{{#things}}{{this}}{{^-last}}|{{/-last}}{{/things}}",
             context("things", Arrays.asList("foo", "bar", "baz")));
    }

    public void testFirstLast () {
        test("[foo]", "{{#things}}{{#-first}}[{{/-first}}{{this}}{{#-last}}]{{/-last}}{{/things}}",
             context("things", Arrays.asList("foo")));
        test("foo", "{{#things}}{{this}}{{^-last}}|{{/-last}}{{/things}}",
             context("things", Arrays.asList("foo")));
    }

    public void testIndex () {
        test("123", "{{#things}}{{-index}}{{/things}}",
             context("things", Arrays.asList("foo", "bar", "baz")));
    }

    public void testLineReporting () {
        String tmpl = "first line\n{{nonexistent}}\nsecond line";
        try {
            Mustache.compiler().compile(tmpl).execute(new Object());
            fail("Referencing a nonexistent variable should throw MustacheException");
        } catch (MustacheException e) {
            assertTrue(e.getMessage().contains("line 2"));
        }
    }

    public void testStandardsModeWithNullValuesInLoop () {
        String tmpl = "first line\n{{#nonexistent}}foo\n{{/nonexistent}}\nsecond line";
        String result = Mustache.compiler().standardsMode(true).compile(tmpl).execute(new Object());
        assertEquals("first line\nsecond line", result);
    }

    public void testStandardsModeWithNullValuesInInverseLoop () {
        String tmpl = "first line\n{{^nonexistent}}foo{{/nonexistent}} \nsecond line";
        String result = Mustache.compiler().standardsMode(true).compile(tmpl).execute(new Object());
        assertEquals("first line\nfoo \nsecond line", result);
    }
/*
    public void testStandardsModeWithDotValue () {
        String tmpl = "{{#foo}}:{{.}}:{{/foo}}";
        String result = Mustache.compiler().standardsMode(true).compile(tmpl).
            execute(Collections.singletonMap("foo", "bar"));
        assertEquals(":bar:", result);
    }

    (expected=MustacheException.class)
    public void testStandardsModeWithNoParentContextSearching () {
        String tmpl = "{{#parent}}foo{{parentProperty}}bar{{/parent}}";
        String result = Mustache.compiler().standardsMode(true).compile(tmpl).
            execute(context("parent", new Object(),
                            "parentProperty", "bar"));
    }

    (expected=MustacheException.class)
    public void testMissingValue () {
        test("n/a", "{{missing}} {{notmissing}}", context("notmissing", "bar"));
    }

    public void testMissingValueWithDefault () {
        test(Mustache.compiler().defaultValue(""),
             "bar", "{{missing}}{{notmissing}}", context("notmissing", "bar"));
    }

    public void testMissingValueWithDefaultNonEmptyString () {
        test(Mustache.compiler().defaultValue("foo"),
             "foobar", "{{missing}}{{notmissing}}", context("notmissing", "bar"));
    }

    public void testNullValueGetsDefault () {
        test(Mustache.compiler().defaultValue("foo"),
             "foobar", "{{nullvar}}{{nonnullvar}}", new Object() {
                 String nonnullvar = "bar";
                 String nullvar = null;
             });
    }
/*
    (expected=MustacheException.class)
    public void testMissingValueWithNullDefault () {
        test(Mustache.compiler().nullValue(""),
             "bar", "{{missing}}{{notmissing}}", new Object() {
                 String notmissing = "bar";
                 // no field or method for 'missing'
             });
    }

    public void testNullValueGetsNullDefault () {
        test(Mustache.compiler().nullValue("foo"),
             "foobar", "{{nullvar}}{{nonnullvar}}", new Object() {
                 String nonnullvar = "bar";
                 String nullvar = null;
             });
    }
*/
    protected void test (Mustache.Compiler compiler, String expected, String template, Object ctx)
    {
        assertEquals(expected, compiler.compile(template).execute(ctx));
    }

    private void assertEquals(String expected, String real) {
		
    	if(expected.equals(real)){
    		System.out.println("OK "+real);
    	}else{
    		System.out.println("NOT OK expected:"+expected+" got:"+real);
    	}
		
	}



	protected void test (String expected, String template, Object ctx)
    {
        test(Mustache.compiler(), expected, template, ctx);
    }

/*    protected Object context (Object... data)
    {
        Map<String, Object> ctx = new HashMap<String, Object>();
        for (int ii = 0; ii < data.length; ii += 2) {
            ctx.put(data[ii].toString(), data[ii+1]);
        }
        return ctx;
    }
  */  
    private Object context(String string, Object object) {
		Map ctx = new FastMap();
		ctx.put(string, object);
		return ctx;
	}
}
