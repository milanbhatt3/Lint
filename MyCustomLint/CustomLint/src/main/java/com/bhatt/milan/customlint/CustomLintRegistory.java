package com.bhatt.milan.customlint;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CustomLintRegistory extends IssueRegistry {
    @NotNull
    @Override
    public List<Issue> getIssues() {
        return new ArrayList<Issue>() {{
            add(LintDetector.ISSUE);
            add(ManifestAttributesDetector.ISSUE_AUTOREMOVE);
            add(ManifestAttributesDetector.ISSUE_EXCLUDE);
        }};
    }
}
