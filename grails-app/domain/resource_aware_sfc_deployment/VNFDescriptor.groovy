package resource_aware_sfc_deployment

class VNFDescriptor {

    Long id
    String name
    String description
    String refId
    String json

    static mapping = {
        id column: "vnfDescriptorId", generator: "sequence"
    }

    static constraints = {

        name(blank: false, nullable: false)
        description(blank: true, nullable: true)
        refId(blank: true, nullable: true)
        json(blank: true, nullable: true)
    }
}
