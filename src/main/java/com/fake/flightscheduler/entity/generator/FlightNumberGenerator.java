package com.fake.flightscheduler.entity.generator;

import java.io.Serializable;
import java.math.BigInteger;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;

import com.fake.flightscheduler.entity.Flight;

public class FlightNumberGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {

		Flight flight = (Flight) obj;

		StringBuilder flightId = new StringBuilder(flight.getAirline());

		Query query = session.createSQLQuery("select FLIGHT_SEQ.nextval as num from dual")
				.addScalar("num", StandardBasicTypes.BIG_INTEGER);

		Long id = ((BigInteger) query.uniqueResult()).longValue();

		return flightId.append(id).toString();
	}

}