package resource_aware_sfc_deployment

class NSDescriptor {

    Long id
    String name
    String description
    String refId
    String json
    VNFDescriptor vnfDescriptorId

    static mapping = {
        id column: "nsDescriptorId", generator: "sequence"
    }

    static constraints = {

        name(blank: false, nullable: false)
        description(blank: true, nullable: true)
        refId(blank: true, nullable: true)
        json(blank: true, nullable: true)
        vnfDescriptorId(blank: true, nullable: true)
    }
}
