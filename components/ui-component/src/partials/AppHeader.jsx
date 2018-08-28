import React from 'react';

export default class AppHeader extends React.Component {
    render(){
        return (<header className="header header-default">
            <div className="container-fluid">
                <div className="pull-left brand">
                    <a href="#">
                        <img src="images/logo-inverse.svg" alt="wso2" title="wso2"
                             className="logo"/>
                            <span>Open Banking Conformance Suite</span>
                    </a>
                </div>
            </div>
        </header>);
    }
}