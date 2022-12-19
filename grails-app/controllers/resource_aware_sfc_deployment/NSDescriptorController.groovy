package resource_aware_sfc_deployment

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class NSDescriptorController {

    NSDescriptorService NSDescriptorService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond NSDescriptorService.list(params), model:[NSDescriptorCount: NSDescriptorService.count()]
    }

    def show(Long id) {
        respond NSDescriptorService.get(id)
    }

    def create() {
        respond new NSDescriptor(params)
    }

    def save(NSDescriptor NSDescriptor) {
        if (NSDescriptor == null) {
            notFound()
            return
        }

        try {
            NSDescriptorService.save(NSDescriptor)
        } catch (ValidationException e) {
            respond NSDescriptor.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'NSDescriptor.label', default: 'NSDescriptor'), NSDescriptor.id])
                redirect NSDescriptor
            }
            '*' { respond NSDescriptor, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond NSDescriptorService.get(id)
    }

    def update(NSDescriptor NSDescriptor) {
        if (NSDescriptor == null) {
            notFound()
            return
        }

        try {
            NSDescriptorService.save(NSDescriptor)
        } catch (ValidationException e) {
            respond NSDescriptor.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'NSDescriptor.label', default: 'NSDescriptor'), NSDescriptor.id])
                redirect NSDescriptor
            }
            '*'{ respond NSDescriptor, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        NSDescriptorService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'NSDescriptor.label', default: 'NSDescriptor'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'NSDescriptor.label', default: 'NSDescriptor'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
