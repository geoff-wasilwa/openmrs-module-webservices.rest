/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 * <p>
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * <p>
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs1_8;


import org.openmrs.PatientProgram;
import org.openmrs.PatientState;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.annotation.SubResource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingSubResource;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

/**
 * {@link Resource} for PatientState, supporting standard CRUD operations
 */
@SubResource(parent = ProgramEnrollmentResource1_8.class, path = "state", supportedClass = PatientState.class, supportedOpenmrsVersions = {"1.8.*", "1.9.*", "1.10.*", "1.11.*", "1.12.*"})
public class PatientStateResource1_8 extends DelegatingSubResource<PatientState, PatientProgram, ProgramEnrollmentResource1_8> {

    @Override
    public String getUri(Object instance) {
        return super.getUri(instance);
    }

    @Override
    public PatientProgram getParent(PatientState instance) {
        return instance.getPatientProgram();
    }

    @Override
    public void setParent(PatientState instance, PatientProgram parent) {
        instance.setPatientProgram(parent);
    }

    @Override
    public PageableResult doGetAll(PatientProgram parent, RequestContext context) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException("Operation not supported.");
    }

    @Override
    public PatientState getByUniqueId(String uniqueId) {
        return Context.getProgramWorkflowService().getPatientStateByUuid(uniqueId);
    }

    @Override
    protected void delete(PatientState delegate, String reason, RequestContext context) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException("Deletion of patient state not supported.");
    }

    @Override
    public PatientState newDelegate() {
        return new PatientState();
    }

    @Override
    public PatientState save(PatientState delegate) {
        PatientProgram parent = delegate.getPatientProgram();
        parent.getStates().add(delegate);
        return delegate;
    }

    @Override
    public void purge(PatientState delegate, RequestContext context) {
        throw new ResourceDoesNotSupportOperationException("Purging of patient state not supported.");
    }

    @Override
    public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
        if (rep instanceof RefRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("state", Representation.REF);
            description.addProperty("uuid");
            description.addProperty("patientProgram", Representation.REF);
            description.addProperty("startDate");
            description.addProperty("endDate");
            description.addSelfLink();
            return description;
        } else if (rep instanceof DefaultRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("state", Representation.DEFAULT);
            description.addProperty("uuid");
            description.addProperty("patientProgram", Representation.DEFAULT);
            description.addProperty("startDate");
            description.addProperty("endDate");
            description.addSelfLink();
            description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
            return description;
        } else if (rep instanceof FullRepresentation) {
            DelegatingResourceDescription description = new DelegatingResourceDescription();
            description.addProperty("state", Representation.FULL);
            description.addProperty("uuid");
            description.addProperty("patientProgram", Representation.FULL);
            description.addProperty("startDate");
            description.addProperty("endDate");
            description.addProperty("voided");
            description.addSelfLink();
            return description;
        }
        return null;
    }

    @Override
    public DelegatingResourceDescription getCreatableProperties() {
        DelegatingResourceDescription d = new DelegatingResourceDescription();
        d.addRequiredProperty("state");
        return d;
    }
}
