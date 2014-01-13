/**
 * JavaTuplesEngine.java
 */
package alice.tuples.javatuples.advanced;

import alice.logictuple.LogicTuple;
import alice.logictuple.TupleArgument;
import alice.tuplecentre.api.exceptions.InvalidOperationException;
import alice.tuplecentre.api.exceptions.InvalidTupleException;
import alice.tuplecentre.api.exceptions.InvalidVarNameException;
import alice.tuples.javatuples.basic.NonCompositeException;

/**
 * @author ste (mailto: s.mariani@unibo.it) on 09/gen/2014
 * 
 */
public class JavaTuplesEngine {

    public static IJavaTuple toJavaTuple(final LogicTuple t) {
        String name = null;
        int nArgs;
        try {
            name = t.getName();
            nArgs = t.getArity();
        } catch (final InvalidOperationException e) {
            nArgs = 0;
        }
        if (nArgs == 0) {
            // TODO if name is a number may cause problems
            return new JavaTupleValue(name);
        }
        try {
            final IJavaTuple tuple =
                    new JavaTuple(name, JavaTuplesEngine.toJavaTuple(t
                            .getArg(0)));
            for (int i = 1; i < nArgs; i++) {
                tuple.addArg(JavaTuplesEngine.toJavaTuple(t.getArg(i)));
            }
            return tuple;
        } catch (final InvalidTupleException e) {
            // cannot happen
        } catch (final InvalidOperationException e) {
            // cannot happen
        } catch (final NonCompositeException e) {
            // cannot happen
        }
        return null;
    }

    public static LogicTuple toLogicTuple(final IJavaTuple t) {
        try {
            return LogicTuple.parse(t.toString());
        } catch (final InvalidTupleException e) {
            // cannot happen
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param arg
     * @return
     */
    private static IJavaTuple toJavaTuple(final TupleArgument arg) {
        if (arg.isAtomic() || arg.isVar()) {
            if (arg.isVar()) {
                try {
                    // TODO what about bound vars?
                    return new JavaTupleVar(arg.getName());
                } catch (final InvalidVarNameException e) {
                    // cannot happen
                    return null;
                } catch (final InvalidOperationException e) {
                    // cannot happen
                    return null;
                }
            }
            // non-var case
            try {
                if (arg.isDouble()) {
                    return new JavaTupleValue(arg.doubleValue());
                } else if (arg.isFloat()) {
                    return new JavaTupleValue(arg.floatValue());
                } else if (arg.isInt()) {
                    return new JavaTupleValue(arg.intValue());
                } else if (arg.isLong()) {
                    return new JavaTupleValue(arg.longValue());
                } else {
                    return new JavaTupleValue(arg.getName());
                }
            } catch (final InvalidOperationException e) {
                // cannot happen
            }
        }
        // list case
        if (arg.isList()) {
            TupleArgument[] a;
            try {
                a = arg.toArray();
                final IJavaTuple list =
                        new JavaTupleList(JavaTuplesEngine.toJavaTuple(a[0]));
                for (int i = 1; i < a.length; i++) {
                    list.addArg(JavaTuplesEngine.toJavaTuple(a[i]));
                }
                return list;
            } catch (final InvalidOperationException e) {
                // cannot happen
            } catch (final NonCompositeException e) {
                // cannot happen
            }
        }
        // compound case
        if (arg.isStruct()) {
            try {
                final int arity = arg.getArity();
                final IJavaTuple tuple =
                        new JavaTuple(arg.getName(),
                                JavaTuplesEngine.toJavaTuple(arg.getArg(0)));
                for (int i = 1; i < arity; i++) {
                    tuple.addArg(JavaTuplesEngine.toJavaTuple(arg.getArg(i)));
                }
                return tuple;
            } catch (final InvalidOperationException e) {
                // cannot happen
            } catch (final InvalidTupleException e) {
                // cannot happen
            } catch (final NonCompositeException e) {
                // cannot happen
            }
        }
        return null;
    }

}
