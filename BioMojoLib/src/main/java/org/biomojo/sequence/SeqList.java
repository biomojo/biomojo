package org.biomojo.sequence;

import org.biomojo.core.Described;
import org.biomojo.core.Identified;
import org.biomojo.property.Propertied;
import org.java0.collection.DefaultList;

public interface SeqList<T extends Seq<?, ?>> extends DefaultList<T>,
		Propertied, Described, Identified {

}
