/*
 * ReSpecT - Copyright (C) aliCE team at deis.unibo.it
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package alice.respect.core.tupleset;

import java.util.LinkedList;

import alice.logictuple.LogicTuple;
import alice.logictuple.TupleArgument;
import alice.logictuple.exceptions.InvalidLogicTupleException;
import alice.logictuple.exceptions.InvalidTupleOperationException;
import alice.respect.core.collection.DoubleKeyMVMap;

/**
 * Class representing a Tuple Set.
 */
public class TupleSetSpec extends AbstractTupleSet {

	public TupleSetSpec() {
		tuples = new DoubleKeyMVMap<String, String, LogicTuple>();
		tAdded = new LinkedList<LTEntry>();
		tRemoved = new LinkedList<LTEntry>();
		transaction = false;
	}

	@Override
	protected String getTupleKey1(LogicTuple t) throws alice.logictuple.exceptions.InvalidLogicTupleException {
		try {
			TupleArgument event = t.getArg(0);
			String s = event.getPredicateIndicator();
			return s;
		} catch (InvalidTupleOperationException e) {
			throw new alice.logictuple.exceptions.InvalidLogicTupleException();
		}
	}

	@Override
	protected String getTupleKey2(LogicTuple t) throws InvalidLogicTupleException {
		try {
			TupleArgument eventArg = t.getArg(0).getArg(0);

			if (eventArg.isNumber())
				return eventArg.toString();
			else if (eventArg.isVar())
				return "VAR";
			else
				return eventArg.getPredicateIndicator();

		} catch (InvalidTupleOperationException e) {
			throw new alice.logictuple.exceptions.InvalidLogicTupleException();
		}
	}

}
