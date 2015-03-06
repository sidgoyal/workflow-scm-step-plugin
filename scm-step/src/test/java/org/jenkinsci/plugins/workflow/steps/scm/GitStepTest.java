/*
 * The MIT License
 *
 * Copyright 2014 Jesse Glick.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.jenkinsci.plugins.workflow.steps.scm;

import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.CredentialsScope;
import com.cloudbees.plugins.credentials.common.IdCredentials;
import com.cloudbees.plugins.credentials.domains.Domain;
import com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl;
import org.jenkinsci.plugins.workflow.steps.Step;
import org.jenkinsci.plugins.workflow.steps.StepConfigTester;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class GitStepTest {

    @Rule public JenkinsRule r = new JenkinsRule();

    @Test
    public void roundtrip() throws Exception {
        GitStep step = new GitStep("git@github.com:jenkinsci/workflow-plugin.git");
        Step roundtrip = new StepConfigTester(r).configRoundTrip(step);
        r.assertEqualDataBoundBeans(step, roundtrip);
    }

    @Test
    public void roundtrip_withcredentials() throws Exception {
        IdCredentials c = new UsernamePasswordCredentialsImpl(CredentialsScope.GLOBAL, null, null, "user", "pass");
        CredentialsProvider.lookupStores(r.jenkins).iterator().next()
                .addCredentials(Domain.global(), c);
        GitStep step = new GitStep("git@github.com:jenkinsci/workflow-plugin.git");
        step.setCredentialsId(c.getId());
        Step roundtrip = new StepConfigTester(r).configRoundTrip(step);
        r.assertEqualDataBoundBeans(step, roundtrip);
    }

}
