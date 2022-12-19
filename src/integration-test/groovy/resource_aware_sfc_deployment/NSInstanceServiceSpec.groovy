package resource_aware_sfc_deployment

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class NSInstanceServiceSpec extends Specification {

    NSInstanceService NSInstanceService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new NSInstance(...).save(flush: true, failOnError: true)
        //new NSInstance(...).save(flush: true, failOnError: true)
        //NSInstance NSInstance = new NSInstance(...).save(flush: true, failOnError: true)
        //new NSInstance(...).save(flush: true, failOnError: true)
        //new NSInstance(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //NSInstance.id
    }

    void "test get"() {
        setupData()

        expect:
        NSInstanceService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<NSInstance> NSInstanceList = NSInstanceService.list(max: 2, offset: 2)

        then:
        NSInstanceList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        NSInstanceService.count() == 5
    }

    void "test delete"() {
        Long NSInstanceId = setupData()

        expect:
        NSInstanceService.count() == 5

        when:
        NSInstanceService.delete(NSInstanceId)
        sessionFactory.currentSession.flush()

        then:
        NSInstanceService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        NSInstance NSInstance = new NSInstance()
        NSInstanceService.save(NSInstance)

        then:
        NSInstance.id != null
    }
}
