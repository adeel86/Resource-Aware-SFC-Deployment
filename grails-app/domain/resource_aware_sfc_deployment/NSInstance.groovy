package resource_aware_sfc_deployment

class NSInstance {

    Long id
    String name
    String numberOfNodes
    String description
    String refId
    String json

    static mapping = {
        id column: "nsInstanceId", generator: "sequence"
    }

    static constraints = {

        name(blank: false, nullable: false)
        numberOfNodes(blank: false, nullable: false)
        description(blank: true, nullable: true)
        refId(blank: true, nullable: true)
        json(blank: true, nullable: true)
    }
}
