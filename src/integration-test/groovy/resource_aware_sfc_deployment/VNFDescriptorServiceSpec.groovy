package resource_aware_sfc_deployment

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class VNFDescriptorServiceSpec extends Specification {

    VNFDescriptorService VNFDescriptorService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new VNFDescriptor(...).save(flush: true, failOnError: true)
        //new VNFDescriptor(...).save(flush: true, failOnError: true)
        //VNFDescriptor VNFDescriptor = new VNFDescriptor(...).save(flush: true, failOnError: true)
        //new VNFDescriptor(...).save(flush: true, failOnError: true)
        //new VNFDescriptor(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //VNFDescriptor.id
    }

    void "test get"() {
        setupData()

        expect:
        VNFDescriptorService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<VNFDescriptor> VNFDescriptorList = VNFDescriptorService.list(max: 2, offset: 2)

        then:
        VNFDescriptorList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        VNFDescriptorService.count() == 5
    }

    void "test delete"() {
        Long VNFDescriptorId = setupData()

        expect:
        VNFDescriptorService.count() == 5

        when:
        VNFDescriptorService.delete(VNFDescriptorId)
        sessionFactory.currentSession.flush()

        then:
        VNFDescriptorService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        VNFDescriptor VNFDescriptor = new VNFDescriptor()
        VNFDescriptorService.save(VNFDescriptor)

        then:
        VNFDescriptor.id != null
    }
}
