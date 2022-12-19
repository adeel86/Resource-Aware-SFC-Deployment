package resource_aware_sfc_deployment

import grails.gorm.services.Service

@Service(NSDescriptor)
interface NSDescriptorService {

    NSDescriptor get(Serializable id)

    List<NSDescriptor> list(Map args)

    Long count()

    void delete(Serializable id)

    NSDescriptor save(NSDescriptor NSDescriptor)

}