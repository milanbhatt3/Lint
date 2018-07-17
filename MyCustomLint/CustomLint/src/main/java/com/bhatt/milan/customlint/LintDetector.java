package com.bhatt.milan.customlint;

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.ULiteralExpression;
import org.jetbrains.uast.UastLiteralUtils;

import java.util.Collections;
import java.util.List;

public class LintDetector extends Detector implements Detector.UastScanner {

    public static final Issue ISSUE = Issue.create(
            "ShortUniqueId",
            "Lint mentions",
            "This check highlights string literals in code which mentions" +
                    "the word 'lint'",
            Category.CORRECTNESS,
            6,
            Severity.WARNING,
            new Implementation(LintDetector.class, Scope.JAVA_FILE_SCOPE)
    );

    @Nullable
    @Override
    public List<Class<? extends UElement>> getApplicableUastTypes() {
        return Collections.singletonList(ULiteralExpression.class);
    }

    @Nullable
    @Override
    public UElementHandler createUastHandler(final JavaContext context) {
        return new UElementHandler() {
            @Override
            public void visitLiteralExpression(ULiteralExpression node) {
                String string = UastLiteralUtils.getValueIfStringLiteral(node);
                if (string == null) {
                    return;
                }
                if (string.contains("lint") && string.matches(".*\\blint\\b.*")) {
                    context.report(ISSUE, node, context.getLocation(node), "This code mentions lint");
                }
            }
        };
    }
}
