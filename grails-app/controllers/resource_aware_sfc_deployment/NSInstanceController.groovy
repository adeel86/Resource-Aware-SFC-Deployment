package resource_aware_sfc_deployment

import grails.config.Config
import grails.plugins.rest.client.RestResponse
import grails.validation.ValidationException
import groovy.ClientOSM

import static org.springframework.http.HttpStatus.*

class NSInstanceController {

    NSInstanceService NSInstanceService

    String url
    String username
    String password

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond NSInstanceService.list(params), model:[NSInstanceCount: NSInstanceService.count()]
    }

    def show(Long id) {
        respond NSInstanceService.get(id)
    }

    def create() {
        respond new NSInstance(params)
    }

    def save(NSInstance nsInstance) {
        if (NSInstance == null) {
            notFound()
            return
        }

        try {

            setConfiguration()

            ClientOSM clientOSM = new ClientOSM(url, username, password)
            RestResponse res = clientOSM.getBearerToken();

            RestResponse response = clientOSM.NSInstantiation(res.getBody().getAt("id").toString(), nsInstance)
            nsInstance.setJson(response.getBody().toString())

            NSInstanceService.save(nsInstance)

        } catch (ValidationException e) {
            respond NSInstance.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'NSInstance.label', default: 'NSInstance'), NSInstance.id])
                redirect NSInstance
            }
            '*' { respond NSInstance, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond NSInstanceService.get(id)
    }

    def update(NSInstance NSInstance) {
        if (NSInstance == null) {
            notFound()
            return
        }

        try {
            NSInstanceService.save(NSInstance)
        } catch (ValidationException e) {
            respond NSInstance.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'NSInstance.label', default: 'NSInstance'), NSInstance.id])
                redirect NSInstance
            }
            '*'{ respond NSInstance, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        NSInstanceService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'NSInstance.label', default: 'NSInstance'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'NSInstance.label', default: 'NSInstance'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    void setConfiguration() {

        url = grailsApplication.config.getProperty('osm.url')
        username = grailsApplication.config.getProperty('osm.username')
        password = grailsApplication.config.getProperty('osm.password')
    }
}
