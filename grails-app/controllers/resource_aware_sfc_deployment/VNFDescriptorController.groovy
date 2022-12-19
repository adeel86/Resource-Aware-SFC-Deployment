package resource_aware_sfc_deployment

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class VNFDescriptorController {

    VNFDescriptorService VNFDescriptorService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond VNFDescriptorService.list(params), model:[VNFDescriptorCount: VNFDescriptorService.count()]
    }

    def show(Long id) {
        respond VNFDescriptorService.get(id)
    }

    def create() {
        respond new VNFDescriptor(params)
    }

    def save(VNFDescriptor VNFDescriptor) {
        if (VNFDescriptor == null) {
            notFound()
            return
        }

        try {
            VNFDescriptorService.save(VNFDescriptor)
        } catch (ValidationException e) {
            respond VNFDescriptor.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'VNFDescriptor.label', default: 'VNFDescriptor'), VNFDescriptor.id])
                redirect VNFDescriptor
            }
            '*' { respond VNFDescriptor, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond VNFDescriptorService.get(id)
    }

    def update(VNFDescriptor VNFDescriptor) {
        if (VNFDescriptor == null) {
            notFound()
            return
        }

        try {
            VNFDescriptorService.save(VNFDescriptor)
        } catch (ValidationException e) {
            respond VNFDescriptor.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'VNFDescriptor.label', default: 'VNFDescriptor'), VNFDescriptor.id])
                redirect VNFDescriptor
            }
            '*'{ respond VNFDescriptor, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        VNFDescriptorService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'VNFDescriptor.label', default: 'VNFDescriptor'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'VNFDescriptor.label', default: 'VNFDescriptor'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
