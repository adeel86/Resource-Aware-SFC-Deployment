package resource_aware_sfc_deployment

import grails.gorm.services.Service

@Service(VNFDescriptor)
interface VNFDescriptorService {

    VNFDescriptor get(Serializable id)

    List<VNFDescriptor> list(Map args)

    Long count()

    void delete(Serializable id)

    VNFDescriptor save(VNFDescriptor VNFDescriptor)

}