import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFaseProyecto, defaultValue } from 'app/shared/model/fase-proyecto.model';

export const ACTION_TYPES = {
  FETCH_FASEPROYECTO_LIST: 'faseProyecto/FETCH_FASEPROYECTO_LIST',
  FETCH_FASEPROYECTO: 'faseProyecto/FETCH_FASEPROYECTO',
  CREATE_FASEPROYECTO: 'faseProyecto/CREATE_FASEPROYECTO',
  UPDATE_FASEPROYECTO: 'faseProyecto/UPDATE_FASEPROYECTO',
  DELETE_FASEPROYECTO: 'faseProyecto/DELETE_FASEPROYECTO',
  RESET: 'faseProyecto/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFaseProyecto>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type FaseProyectoState = Readonly<typeof initialState>;

// Reducer

export default (state: FaseProyectoState = initialState, action): FaseProyectoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FASEPROYECTO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FASEPROYECTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FASEPROYECTO):
    case REQUEST(ACTION_TYPES.UPDATE_FASEPROYECTO):
    case REQUEST(ACTION_TYPES.DELETE_FASEPROYECTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_FASEPROYECTO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FASEPROYECTO):
    case FAILURE(ACTION_TYPES.CREATE_FASEPROYECTO):
    case FAILURE(ACTION_TYPES.UPDATE_FASEPROYECTO):
    case FAILURE(ACTION_TYPES.DELETE_FASEPROYECTO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_FASEPROYECTO_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_FASEPROYECTO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FASEPROYECTO):
    case SUCCESS(ACTION_TYPES.UPDATE_FASEPROYECTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FASEPROYECTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/fase-proyectos';

// Actions

export const getEntities: ICrudGetAllAction<IFaseProyecto> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_FASEPROYECTO_LIST,
    payload: axios.get<IFaseProyecto>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IFaseProyecto> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FASEPROYECTO,
    payload: axios.get<IFaseProyecto>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IFaseProyecto> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FASEPROYECTO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFaseProyecto> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FASEPROYECTO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFaseProyecto> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FASEPROYECTO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
