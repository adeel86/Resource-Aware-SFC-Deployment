package resource_aware_sfc_deployment

import grails.gorm.services.Service

@Service(NSInstance)
interface NSInstanceService {

    NSInstance get(Serializable id)

    List<NSInstance> list(Map args)

    Long count()

    void delete(Serializable id)

    NSInstance save(NSInstance NSInstance)

}