#! /usr/bin/env bash

GWT_VERSION=2.5.1
if [ ! -d $HOME/gwt-$GWT_VERSION ]; then
    rm -rf $HOME/gwt-$GWT_VERSION
    echo "Downloading GWT library."
    wget http://google-web-toolkit.googlecode.com/files/gwt-$GWT_VERSION.zip \
	-O $HOME/gwt-$GWT_VERSION.zip
    unzip -qq gwt-$GWT_VERSION.zip -d $HOME/gwt-$GWT_VERSION
else
    echo "Using cached GWT directory."
fi
