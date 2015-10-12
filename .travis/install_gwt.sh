#! /usr/bin/env bash

GWT_VERSION=2.5.1
if [ ! -f $HOME/gwt-$GWT_VERSION/gwt-servlet.jar ]; then
    echo "Downloading GWT library."
    cd $HOME
    wget http://google-web-toolkit.googlecode.com/files/gwt-$GWT_VERSION.zip
    unzip -qq gwt-$GWT_VERSION.zip
else
    echo "Using cached GWT directory."
fi
