package uib.secom.accestur.shared.service;

import uib.secom.accestur.shared.model.DomainObject;

public interface ServiceInterface<T extends DomainObject> {

	T read(final T domainObject);
	
	T update(final T domainObject);
		
}