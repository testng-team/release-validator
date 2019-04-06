package org.testng.sample;

import java.util.List;
import org.testng.IAlterSuiteListener;
import org.testng.IAnnotationTransformer;
import org.testng.IConfigurationListener;
import org.testng.IDataProviderListener;
import org.testng.IExecutionListener;
import org.testng.IExecutionVisualiser;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

public class MasterListener
    implements IAnnotationTransformer,
    IConfigurationListener,
    IDataProviderListener,
    ITestListener,
    IInvokedMethodListener,
    ISuiteListener,
    IAlterSuiteListener,
    IExecutionListener,
    IExecutionVisualiser,
    IMethodInterceptor,
    IReporter {

  @Override
  public void consumeDotDefinition(String s) {}

  @Override
  public void alter(List<XmlSuite> suites) {}

  @Override
  public void onExecutionStart() {}

  @Override
  public void onExecutionFinish() {}

  @Override
  public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {}

  @Override
  public void afterInvocation(IInvokedMethod method, ITestResult testResult) {}

  @Override
  public void beforeInvocation(
      IInvokedMethod method, ITestResult testResult, ITestContext context) {}

  @Override
  public void afterInvocation(
      IInvokedMethod method, ITestResult testResult, ITestContext context) {}

  @Override
  public void generateReport(
      List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {}

  @Override
  public void onStart(ISuite suite) {}

  @Override
  public void onFinish(ISuite suite) {}

  @Override
  public void onTestStart(ITestResult result) {}

  @Override
  public void onTestSuccess(ITestResult result) {}

  @Override
  public void onTestFailure(ITestResult result) {}

  @Override
  public void onTestSkipped(ITestResult result) {}

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

  @Override
  public void onTestFailedWithTimeout(ITestResult result) {}

  @Override
  public void onStart(ITestContext context) {}

  @Override
  public void onFinish(ITestContext context) {}

  @Override
  public List<IMethodInstance> intercept(List<IMethodInstance> list, ITestContext iTestContext) {
    return null;
  }
}
