package org.biomojo.property;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.biomojo.sequence.AbstractSeq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@DiscriminatorValue(value = "E")
public class SeqProperty extends BasicProperty {
	/**
	 *
	 */
	private static final long serialVersionUID = 8959250872527073219L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory
			.getLogger(SeqProperty.class.getName());

	@ManyToOne
	@JoinColumn(name = "longValue")
	private AbstractSeq<?, ?> sequence;

	@Override
	public Object getValue() {
		return sequence;
	}

	@Override
	public void setValue(final Object value) {
		sequence = (AbstractSeq<?, ?>) value;
	}
}
