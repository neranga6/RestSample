package samplerest

import grails.converters.JSON;
import grails.converters.XML

import java.beans.XMLDecoder;

import org.springframework.dao.DataIntegrityViolationException

class RunnerController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [runnerInstanceList: Runner.list(params), runnerInstanceTotal: Runner.count()]
    }

    def create() {
        [runnerInstance: new Runner(params)]
    }

    def save() {
        def runnerInstance = new Runner(params)
        if (!runnerInstance.save(flush: true)) {
            render(view: "create", model: [runnerInstance: runnerInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'runner.label', default: 'Runner'), runnerInstance.id])
        redirect(action: "show", id: runnerInstance.id)
    }

    def show(Long id) {
        /*def runnerInstance = Runner.get(id)
        if (!runnerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'runner.label', default: 'Runner'), id])
            redirect(action: "list")
            return
        }

        [runnerInstance: runnerInstance]*/
		if(params.firstName && Runner.exists(params.firstName)){
			render Runner.findByFirstName(params.firstName) as XML
		}
		else{
			render Runner.list() as XML
		}
    }

    def edit(Long id) {
        def runnerInstance = Runner.get(id)
        if (!runnerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'runner.label', default: 'Runner'), id])
            redirect(action: "list")
            return
        }

        [runnerInstance: runnerInstance]
    }

    def update(Long id, Long version) {
        def runnerInstance = Runner.get(id)
        if (!runnerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'runner.label', default: 'Runner'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (runnerInstance.version > version) {
                runnerInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'runner.label', default: 'Runner')] as Object[],
                          "Another user has updated this Runner while you were editing")
                render(view: "edit", model: [runnerInstance: runnerInstance])
                return
            }
        }

        runnerInstance.properties = params

        if (!runnerInstance.save(flush: true)) {
            render(view: "edit", model: [runnerInstance: runnerInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'runner.label', default: 'Runner'), runnerInstance.id])
        redirect(action: "show", id: runnerInstance.id)
    }

    def delete(Long id) {
        def runnerInstance = Runner.get(id)
        if (!runnerInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'runner.label', default: 'Runner'), id])
            redirect(action: "list")
            return
        }

        try {
            runnerInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'runner.label', default: 'Runner'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'runner.label', default: 'Runner'), id])
            redirect(action: "show", id: id)
        }
    }
}
