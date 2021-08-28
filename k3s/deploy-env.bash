#!/usr/bin/env bash -ex

istioctl install --set profile=demo \
  --set meshConfig.accessLogFile="/dev/stdout" \
  --set meshConfig.accessLogEncoding=JSON \
  --set meshConfig.defaultConfig.tracing.zipkin.address=zipkin.istio-system:9411 \
  -y

kubectl label namespace default istio-injection=enabled



# Deploy the resources and wait for their pods to become ready
kubectl apply -f ../k3s
kubectl apply -f ../k3s-dashboard
kubectl apply -f ../k3s-dashboard
kubectl wait --timeout=600s --for=condition=ready pod --all
kubectl wait --timeout=600s --for=condition=available deployment --all